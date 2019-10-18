package com.kotlindemo.mvp.model

import com.kotlindemo.mode.HomeBean
import com.kotlindemo.net.RetrofitManager
import com.kotlindemo.rx.SchedulerUtil
import io.reactivex.Observable


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
 * @date：2019/10/17 16:49
 * @version:1.0.0
 * @description: HomeModel
 */
class FollowModel {

    fun requestFollowList(): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getFollowInfo()
            .compose(SchedulerUtil.ioToMain())
    }


    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtil.ioToMain())
    }
}