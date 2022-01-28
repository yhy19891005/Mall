package com.yhy.message.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.message.data.protocol.Message
import com.yhy.message.presenter.view.MsgView
import com.yhy.message.service.MsgService
import javax.inject.Inject

class MsgPresenter @Inject constructor(): BasePresenter<MsgView>()  {

    //dagger注入MsgService实例
    @Inject
    lateinit var service: MsgService

    /**
     * 获取消息列表
     * */
    fun getMessageList(){
        if(!checkNet()) return
        mView.showLoading()

        service.getMessageList()
            .excute(object : BaseSubscriber<MutableList<Message>?>(mView){
                override fun onNext(t: MutableList<Message>?) {
                    mView.onGetMsgResult(t)
                }
            },provider)
    }
}