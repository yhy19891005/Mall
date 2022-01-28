package com.yhy.user.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.user.presenter.view.ForgetPwdView
import com.yhy.user.service.UserService
import javax.inject.Inject

class ForgetPwdPresenter @Inject constructor(): BasePresenter<ForgetPwdView>() {

    //dagger注入UserService实例
    @Inject
    lateinit var service: UserService

    fun forgetPwd(mobile:String, code: String){

        if(!checkNet()) return
        mView.showLoading()

        service.forgetPwd(mobile,code)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onForgetPwdResult(t)
                }
            },provider)
    }
}


