package com.kotlindemo.mode

import java.io.Serializable
/***********************************************************
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
 **************************************************************
 * @author: 李刘欢
 * @date：2019/10/17 16:41
 * @version:1.0.0
 * @description: AuthorInfoBean.kt
 */

data class AuthorInfoBean(val tabInfo: TabInfo,
                          val pgcInfo: PgcInfo) : Serializable {

    data class TabInfo(val tabList: List<TabList>,
                       val defaultIdx: Int) : Serializable

    data class TabList(val id: Int,
                       val name: String,
                       val apiUrl: String) : Serializable


    data class PgcInfo(val dataType: String,
                       val id: Int,
                       val icon: String,
                       val name: String,
                       val brief: String,
                       val description: String,
                       val actionUrl: String,
                       val area: String,
                       val gender: String,
                       val registDate: Int,
                       val followCount: Int,
                       val follow: Follow,
                       val self: Boolean,
                       val cover: String,
                       val videoCount: Int,
                       val shareCount: Int,
                       val collectCount: Int,
                       val shield: Shield) : Serializable


    data class Follow(val itemType: String,
                      val itemId: Int,
                      val followed: Boolean) : Serializable

    data class Shield(val itemType: String,
                      val itemId: Int,
                      val shielded: Boolean) : Serializable
}


