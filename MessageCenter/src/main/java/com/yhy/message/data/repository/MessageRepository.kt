package com.yhy.message.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.message.data.api.MessageApi
import com.yhy.message.data.protocol.Message
import rx.Observable
import javax.inject.Inject

class MessageRepository @Inject constructor() {

    /**
     * 获取消息列表
     * */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>{
        return RetrofitFactory.INSTANCE
                              .create(MessageApi::class.java)
                              .getMessageList()
    }
}