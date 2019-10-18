package com.kotlindemo.rx

/**
 * Created by xuhao on 2017/11/17.
 * desc:
 */

object SchedulerUtil {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
