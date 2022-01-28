package com.yhy.user.service

import com.yhy.user.data.protocol.UserInfo
import rx.Observable

interface UpLoadService {

    /**
     * 获取七牛云上传token
     * */
    fun getUpLoadToken(): Observable<String>

}