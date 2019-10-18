package com.kotlindemo.util

import android.content.Context
import android.util.DisplayMetrics

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
 * @date：2019/10/17 15:13
 * @version:1.0.0
 * @description: DisplayManager
 */
object DisplayManager {
    private var displayMetrics: DisplayMetrics? = null

    var screenWidth: Int? = null

    var screenHeight: Int? = null

    private var screenDpi: Int? = null

    fun init(mContext: Context) {
        displayMetrics = mContext.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }

    fun dip2px(dipValue: Float): Int? {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }
}