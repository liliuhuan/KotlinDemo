package com.kotlindemo.ui.fragment

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.base.BaseFragment
import kotlinx.android.synthetic.main.activity_search.*


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
class MineFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(mTitle: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = mTitle
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        mTextView.text = mTitle
    }

}