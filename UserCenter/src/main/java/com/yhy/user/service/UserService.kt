package com.yhy.user.service

import com.yhy.user.data.protocol.UserInfo
import rx.Observable

interface UserService {

    /**
     * 注册
     * */
    fun register(mobile: String, code: String, psw: String): Observable<Boolean>

    /**
     * 登录
     * */
    fun login(mobile:String, psw: String, pushId: String): Observable<UserInfo>

    /**
     * 忘记密码第一步: 验证码
     * */
    fun forgetPwd(mobile: String, code: String): Observable<Boolean>

    /**
     * 忘记密码第二步: 重置密码
     * */
    fun resetPwd(mobile:String, psw: String): Observable<Boolean>

    /**
     * 编辑用户资料
     * */
    fun editUser(userIcon: String, userName: String, gender: String, sign: String): Observable<UserInfo>
}