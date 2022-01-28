package com.yhy.user.data.api

import com.yhy.base.data.protocol.BaseResp
import com.yhy.user.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {

    /**
     * 注册
     * */
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    /**
     * 登录
     * */
    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    /**
     * 忘记密码第一步: 验证码
     * */
    @POST("userCenter/forgetPwd")
    fun forgetPwd(@Body req: ForgetPwdReq): Observable<BaseResp<String>>

    /**
     * 忘记密码第二步: 重置密码
     * */
    @POST("userCenter/resetPwd")
    fun resetPwd(@Body req: ResetPwdReq): Observable<BaseResp<String>>

    /**
     * 编辑用户资料
     * */
    @POST("userCenter/editUser")
    fun editUser(@Body req: EditUserReq): Observable<BaseResp<UserInfo>>
}