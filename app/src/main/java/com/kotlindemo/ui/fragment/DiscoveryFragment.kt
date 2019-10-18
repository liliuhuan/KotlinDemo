package com.kotlindemo.ui.fragment

import androidx.fragment.app.Fragment
import com.kotlindemo.R
import com.kotlindemo.base.BaseFragment
import com.kotlindemo.base.BaseFragmentAdapter
import com.kotlindemo.ui.fragment.discovery.CategoryFragment
import com.kotlindemo.ui.fragment.discovery.FollowFragment
import com.kotlindemo.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_discovery.*


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
class DiscoveryFragment : BaseFragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    companion object {
        fun getInstance(mTitle: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            fragment.mTitle = mTitle
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_discovery

    override fun initView() {
        activity?.let { StatusBarUtil.setPaddingSmart(it, mDisToolBar) }
        mTitleTextView.text = mTitle

        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
    }
}