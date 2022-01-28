package com.yhy.user.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.presenter.view.LoginView
import com.yhy.user.presenter.view.ResetPwdView
import com.yhy.user.presenter.view.UserInfoView
import com.yhy.user.service.UpLoadService
import com.yhy.user.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor(): BasePresenter<UserInfoView>() {

    //dagger注入UpLoadService实例
    @Inject
    lateinit var upLoadService: UpLoadService

    //dagger注入UserService实例
    @Inject
    lateinit var userService: UserService

    fun getUpLoadToken(){
        if(!checkNet()) return
        mView.showLoading()

        upLoadService.getUpLoadToken()
            .excute(object : BaseSubscriber<String>(mView){
                override fun onNext(t: String) {
                    mView.onGetUpLoadTokenResult(t)
                }
            },provider)
    }

    fun editUser(userIcon: String, userName: String, gender: String, sign: String){
        if(!checkNet()) return
        mView.showLoading()

        userService.editUser(userIcon, userName, gender, sign)
            .excute(object : BaseSubscriber<UserInfo>(mView){
                override fun onNext(t: UserInfo) {
                    mView.onEditUserResult(t)
                }
            },provider)
    }
}


