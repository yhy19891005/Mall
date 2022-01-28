package com.yhy.user.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.user.data.api.UserApi
import com.yhy.user.data.protocol.*
import rx.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(){

    /**
     * 注册
     * */
    fun register(mobile:String, code: String,psw: String): Observable<BaseResp<String>>{
       return RetrofitFactory.INSTANCE
                             .create(UserApi::class.java)
                             .register(RegisterReq(mobile, code, psw))
    }

    /**
     * 登录
     * */
    fun login(mobile:String, psw: String,pushId: String): Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.INSTANCE
            .create(UserApi::class.java)
            .login(LoginReq(mobile, psw, pushId))
    }

    /**
     * 忘记密码第一步: 验证码
     * */
    fun forgetPwd(mobile:String, code: String): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(UserApi::class.java)
            .forgetPwd(ForgetPwdReq(mobile, code))
    }

    /**
     * 忘记密码第二步: 重置密码
     * */
    fun resetPwd(mobile:String, psw: String): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(UserApi::class.java)
            .resetPwd(ResetPwdReq(mobile, psw))
    }

    /**
     * 编辑用户资料
     * */
    fun editUser(userIcon: String, userName: String, gender: String, sign: String): Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.INSTANCE
            .create(UserApi::class.java)
            .editUser(EditUserReq(userIcon, userName, gender, sign))
    }
}