package com.yhy.message.service

import com.yhy.message.data.protocol.Message
import rx.Observable

interface MsgService {

    /**
     * 获取消息列表
     * */
    fun getMessageList(): Observable<MutableList<Message>?>
}