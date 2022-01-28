package com.yhy.user.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.presenter.view.LoginView
import com.yhy.user.service.UserService
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginView>() {

    //dagger注入UserService实例
    @Inject
    lateinit var service: UserService

    fun login(mobile:String, psw: String, pushId: String){

        if(!checkNet()) return
        mView.showLoading()

        service.login(mobile,psw,pushId)
            .excute(object : BaseSubscriber<UserInfo>(mView){
                override fun onNext(t: UserInfo) {
                    mView.onLoginResult(t)
                }
            },provider)
    }
}


