package com.kotlindemo.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * Created by xuhao on 2017/11/17.
 * desc:
 */


class ComputationMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())
