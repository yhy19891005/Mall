package com.yhy.order.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.order.data.protocol.Order
import com.yhy.order.presenter.view.StatusOrderView
import com.yhy.order.service.OrderService
import javax.inject.Inject

class StatusOrderPresenter @Inject constructor(): BasePresenter<StatusOrderView>() {

    //dagger注入CartService实例
    @Inject
    lateinit var service: OrderService

    /**
     * 根据订单状态查询查询订单列表
     * */
    fun getOrderList(orderStatus: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getOrderList(orderStatus)
            .excute(object : BaseSubscriber<MutableList<Order>?>(mView){
                override fun onNext(t: MutableList<Order>?) {
                    mView.onGetStatusOrderResult(t)
                }
            },provider)
    }

    /**
     * 确认订单
     * */
    fun confirmOrder(orderId:Int){
        if(!checkNet()) return
        mView.showLoading()

        service.confirmOrder(orderId)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onConfirmOrderResult(t)
                }
            },provider)
    }

    /**
     * 取消订单
     * */
    fun cancelOrder(orderId:Int){
        if(!checkNet()) return
        mView.showLoading()

        service.cancelOrder(orderId)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onCancelOrderResult(t)
                }
            },provider)
    }

}