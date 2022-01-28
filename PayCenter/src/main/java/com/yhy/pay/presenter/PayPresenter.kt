package com.yhy.pay.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.pay.presenter.view.PayView
import com.yhy.pay.service.PayService
import javax.inject.Inject

class PayPresenter @Inject constructor(): BasePresenter<PayView>() {

    //dagger注入PayService实例
    @Inject
    lateinit var service: PayService

    /**
     * 获取支付宝支付签名
     * */
    fun getPaySign(orderId: Int, totalPrice: Long){

        if(!checkNet()) return
        mView.showLoading()

        service.getPaySign(orderId, totalPrice)
            .excute(object : BaseSubscriber<String>(mView){
                override fun onNext(t: String) {
                    mView.onGetPaySignResult(t)
                }
            },provider)
    }

    /**
     * 刷新订单状态，已支付
     * */
    fun payOrder(orderId:Int){
        if(!checkNet()) return
        mView.showLoading()

        service.payOrder(orderId)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onPayOrderResult(t)
                }
            },provider)
    }
}