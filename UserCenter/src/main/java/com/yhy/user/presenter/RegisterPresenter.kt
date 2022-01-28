package com.yhy.user.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.user.presenter.view.RegisterView
import com.yhy.user.service.UserService
import com.yhy.user.service.impl.UserServiceImpl
import javax.inject.Inject
import javax.inject.Named

class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {

    //dagger注入UserService实例
    //java代码里面可以 @Named("service"),kotlin里面不行
    @Inject
    lateinit var service: UserService

    fun register(mobile:String, code: String,psw: String){

//        val service = UserServiceImpl()

        if(!checkNet()) return
        mView.showLoading()

        service.register(mobile,code,psw)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    if(t){
                        mView.onRegisterResult("注册成功")
                    }else{
                        mView.onRegisterResult("注册失败")
                    }

                }
            },provider)
        //相同操作,抽取整扩展方法
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
        //公共部分抽取成BaseSubscriber
//            .subscribe(object : BaseSubscriber<Boolean>(){
//
//                override fun onNext(t: Boolean) {
//                    mView.onRegisterResult(t)
//                }
//            })
    }

}


