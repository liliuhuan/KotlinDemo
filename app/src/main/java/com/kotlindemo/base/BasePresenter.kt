package com.kotlindemo.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


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
 * @date：2019/10/17 15:52
 * @version:1.0.0
 * @description: BasePresenter
 */
open class BasePresenter<V : IBaseView> : IPresenter<V> {
    var mRootView: V? = null

    private val isViewAttached: Boolean
        get() = mRootView != null

    private val compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: V) {
        this.mRootView = mRootView
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun detachView() {
        if (mRootView != null) mRootView = null
        if (!compositeDisposable.isDisposed) compositeDisposable.clear()
    }


    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() :
        RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


}

