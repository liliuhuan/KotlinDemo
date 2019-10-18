package com.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.kotlindemo.base.BasePresenter
import com.kotlindemo.mvp.contract.FollowContract
import com.kotlindemo.mvp.model.FollowModel


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
 * @date：2019/10/18 15:26
 * @version:1.0.0
 * @description: FollowPresenter
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy { FollowModel() }

    private var nextPageUrl: String? = null

    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
            .subscribe({ issue ->
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = issue.nextPageUrl
                    setFollowInfo(issue)
                }
            }, { throwable ->
                mRootView?.apply {
                    //处理异常
                    showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                .subscribe({ issue->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }

                },{ t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}