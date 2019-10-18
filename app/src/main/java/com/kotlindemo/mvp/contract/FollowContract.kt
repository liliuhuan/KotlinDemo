package com.kotlindemo.mvp.contract

import com.kotlindemo.base.IBaseView
import com.kotlindemo.mode.HomeBean


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
 * @date：2019/10/17 16:45
 * @version:1.0.0
 * @description: HomeContract
 */
interface FollowContract {
    interface View : IBaseView {

        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter {

        fun requestFollowList()

        fun loadMoreData()
    }
}