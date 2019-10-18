package com.kotlindemo.ui.fragment.discovery

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.kotlindemo.R
import com.kotlindemo.base.BaseFragment
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.mvp.contract.FollowContract
import com.kotlindemo.mvp.presenter.FollowPresenter
import com.kotlindemo.showToast
import com.kotlindemo.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.fragment_follow.*


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
 * @date：2019/10/18 15:03
 * @version:1.0.0
 * @description: FollowFragment
 */
class FollowFragment : BaseFragment(), FollowContract.View {


    private var mTitle: String? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mPresenter by lazy { FollowPresenter() }

    private val mFollowAdapter by lazy { activity?.let { FollowAdapter(it, itemList) } }

    private var loadingMore = false


    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(mTitle: String): FollowFragment {
            val fragment = FollowFragment()
            fragment.mTitle = mTitle
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_follow

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager?.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount?.minus(1))) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore = false
        itemList = issue.itemList
        mFollowAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }
}