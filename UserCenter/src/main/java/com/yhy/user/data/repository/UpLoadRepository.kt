package com.yhy.user.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.user.data.api.UpLoadApi
import rx.Observable
import javax.inject.Inject

class UpLoadRepository @Inject constructor(){

    /**
     * 注册
     * */
    fun getUpLoadToken(): Observable<BaseResp<String>>{
       return RetrofitFactory.INSTANCE
                             .create(UpLoadApi::class.java)
                             .getUpLoadToken()
    }
}