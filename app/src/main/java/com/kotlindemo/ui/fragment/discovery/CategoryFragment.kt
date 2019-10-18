package com.kotlindemo.ui.fragment.discovery

import com.kotlindemo.R
import com.kotlindemo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_categroy.*


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
class CategoryFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(mTitle: String): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.mTitle = mTitle
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_categroy

    override fun initView() {
        mTextView.text = mTitle

    }

}