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
class HomeModel {
    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num:Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtil.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{

        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtil.ioToMain())
    }
}