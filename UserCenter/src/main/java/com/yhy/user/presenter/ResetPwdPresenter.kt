package com.yhy.user.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.presenter.view.LoginView
import com.yhy.user.presenter.view.ResetPwdView
import com.yhy.user.service.UserService
import javax.inject.Inject

class ResetPwdPresenter @Inject constructor(): BasePresenter<ResetPwdView>() {

    //dagger注入UserService实例
    @Inject
    lateinit var service: UserService

    fun resetPwd(mobile:String, psw: String){

        if(!checkNet()) return
        mView.showLoading()

        service.resetPwd(mobile,psw)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onResetPwdResult(t)
                }
            },provider)
    }
}


