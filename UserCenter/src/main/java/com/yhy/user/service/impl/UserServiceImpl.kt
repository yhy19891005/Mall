package com.yhy.user.service.impl

import com.yhy.base.ext.covert
import com.yhy.base.ext.covertBoolean
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.data.repository.UserRepository
import com.yhy.user.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {

    //UserRepository实例,dagger注入
    @Inject
    lateinit var repository: UserRepository

    /**
     * 注册
     * */
    override fun register(mobile: String, code: String, psw: String): Observable<Boolean>{

//        val repository = UserRepository()

        //使用自定义扩展方法
        return repository.register(mobile, code, psw).covertBoolean()

//        return repository.register(mobile, code, psw).flatMap(BaseFuncBoolean())

        //抽取成BaseFuncBoolean()
//            .flatMap { t ->
//                if (t.state != 0) {
//                   return@flatMap Observable.error(BaseException(t.state, t.msg))
//                }
//                Observable.just(true)
//            }
    }

    /**
     * 登录
     * */
    override fun login(mobile: String, psw: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, psw, pushId).covert()
    }

    /**
     * 忘记密码第一步: 验证码
     * */
    override fun forgetPwd(mobile: String, code: String): Observable<Boolean> {
        return repository.forgetPwd(mobile, code).covertBoolean()
    }

    /**
     * 忘记密码第二步: 重置密码
     * */
    override fun resetPwd(mobile:String, psw: String): Observable<Boolean> {
        return repository.resetPwd(mobile, psw).covertBoolean()
    }

    /**
     * 编辑用户资料
     * */
    override fun editUser(userIcon: String, userName: String, gender: String, sign: String): Observable<UserInfo>{
        return repository.editUser(userIcon, userName, gender, sign).covert()
    }
}