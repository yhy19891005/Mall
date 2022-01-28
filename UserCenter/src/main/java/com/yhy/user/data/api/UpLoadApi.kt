package com.yhy.user.data.api

import com.yhy.base.data.protocol.BaseResp
import com.yhy.user.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UpLoadApi {

    /**
     * 获取七牛云上传token
     * */
    @POST("common/getUploadToken")
    fun getUpLoadToken(): Observable<BaseResp<String>>

}