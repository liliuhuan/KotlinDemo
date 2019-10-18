package com.kotlindemo.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.kotlindemo.R
import com.kotlindemo.base.BaseFragment
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.mvp.contract.HomeContract
import com.kotlindemo.mvp.presenter.HomePresenter
import com.kotlindemo.showToast
import com.kotlindemo.ui.activity.SearchActivity
import com.kotlindemo.ui.adapter.HomeAdapter
import com.kotlindemo.util.StatusBarUtil
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/************************************************************
 *
 *
 *                   .::::.
 *                  .::::::::.
 *                 :::::::::::  COME ON BABY
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *************************************************************
 * @author: 李刘欢
 * @date：2019/10/17 17:10
 * @version:1.0.0
 * @description: HomeFragment
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mPresenter by lazy { HomePresenter() }

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHomeAdapter: HomeAdapter? = null

    private var loadingMore = false

    private var isRefresh = false

    private var mMaterialHeader: MaterialHeader? = null


    companion object {
        fun getInstance(mTitle: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = mTitle
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView() {
        mPresenter.attachView(this)

        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader
        mMaterialHeader!!.setShowBezierWave(true)

        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager?.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        iv_search.setOnClickListener { openSearchActivity() }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = activity?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName)
            }
            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)
        mHomeAdapter = activity?.let { HomeAdapter(it, homeBean.issueList[0].itemList) }
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        if (!isRefresh){
            isRefresh = false
            multipleStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
       mRefreshLayout.finishRefresh()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}