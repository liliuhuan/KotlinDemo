package com.kotlindemo.api

import com.kotlindemo.mode.AuthorInfoBean
import com.kotlindemo.mode.CategoryBean
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.mode.TabInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


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
 * @date：2019/10/17 16:04
 * @version:1.0.0
 * @description: ApiService
 */
interface ApiService {
    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num:Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<CategoryBean>>

    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Observable<HomeBean.Issue>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeBean.Issue>

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList():Observable<TabInfoBean>

    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query :String) : Observable<HomeBean.Issue>

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWord():Observable<ArrayList<String>>

    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo():Observable<HomeBean.Issue>

    /**
     * 作者信息
     */
    @GET("v4/pgcs/detail/tab?")
    fun getAuthorInfo(@Query("id") id: Long):Observable<AuthorInfoBean>
}