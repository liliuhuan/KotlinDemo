package com.kotlindemo.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


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
 * @date：2019/10/17 15:39
 * @version:1.0.0
 * @description: BaseFragmentAdapter
 */
class BaseFragmentAdapter : FragmentPagerAdapter {
    private var fragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>?, mTitles: List<String>?) : super(fm) {
        this.fragmentList = fragmentList
        this.mTitles = mTitles

        setFragments(fm, fragmentList, mTitles)
    }

    private fun setFragments(fm: FragmentManager, fragmentList: List<Fragment>?, mTitles: List<String>?) {
        this.mTitles = mTitles
        fragmentList?.apply {
            val beginTransaction = fm.beginTransaction()
            fragmentList.forEach { beginTransaction.remove(it) }
            beginTransaction.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragmentList
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles!![position]
    }
}