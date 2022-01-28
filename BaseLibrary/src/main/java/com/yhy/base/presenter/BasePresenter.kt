package com.yhy.base.presenter

import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.yhy.base.presenter.view.BaseView
import com.yhy.base.utils.NetWorkUtils
import javax.inject.Inject

open class BasePresenter<T: BaseView> {
    lateinit var mView: T

    @Inject
    lateinit var provider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNet(): Boolean {
        if(!NetWorkUtils.isNetWorkAvailable(context)){
            mView.onError("网络不可用")
            return false
        }
        return true
    }
}