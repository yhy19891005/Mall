package com.yhy.user.service.impl

import com.yhy.base.ext.covert
import com.yhy.user.data.repository.UpLoadRepository
import com.yhy.user.service.UpLoadService
import rx.Observable
import javax.inject.Inject

class UpLoadServiceImpl  @Inject constructor(): UpLoadService {

    //UpLoadRepository实例,dagger注入
    @Inject
    lateinit var repository: UpLoadRepository

    /**
     * 获取七牛云上传token
     * */
    override fun getUpLoadToken(): Observable<String> = repository.getUpLoadToken().covert()

}