package com.yhy.base.rx

import com.yhy.base.presenter.view.BaseView
import rx.Subscriber


open class BaseSubscriber<T>(var view: BaseView): Subscriber<T>() {

    override fun onCompleted() {
        view.hideLoading()
    }

    override fun onError(e: Throwable?) {
        view.hideLoading()
        if(e is BaseException){
            view.onError(e.msg)
        }
    }

    override fun onNext(t: T) {

    }
}