package com.yhy.message.service.impl

import com.yhy.base.ext.covert
import com.yhy.message.data.protocol.Message
import com.yhy.message.data.repository.MessageRepository
import com.yhy.message.service.MsgService
import rx.Observable
import javax.inject.Inject

class MsgServiceImpl @Inject constructor(): MsgService {

    //MessageRepository实例,dagger注入
    @Inject
    lateinit var repository: MessageRepository

    /**
     * 获取消息列表
     * */
    override fun getMessageList(): Observable<MutableList<Message>?> = repository.getMessageList().covert()
}