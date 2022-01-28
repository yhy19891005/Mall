package com.yhy.message.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.message.data.protocol.Message

interface MsgView: BaseView {

    fun onGetMsgResult(result: MutableList<Message>?)
}