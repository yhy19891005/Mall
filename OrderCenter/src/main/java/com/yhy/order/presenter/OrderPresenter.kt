package com.yhy.order.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.order.data.protocol.Order
import com.yhy.order.presenter.view.OrderView
import com.yhy.order.service.OrderService
import javax.inject.Inject

class OrderPresenter @Inject constructor(): BasePresenter<OrderView>() {

    //dagger注入OrderService实例
    @Inject
    lateinit var service: OrderService

    /**
     *  根据ID获取订单
     * */
    fun getOrderById(orderId: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getOrderById(orderId)
            .excute(object : BaseSubscriber<Order>(mView){
                override fun onNext(t: Order) {
                    mView.onGetOrderGoodsResult(t)
                }
            },provider)
    }

    /**
     * 提交订单
     * */
    fun submitOrder(order: Order){
        if(!checkNet()) return
        mView.showLoading()

        service.submitOrder(order)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onSubmitOrderResult(t)
                }
            },provider)
    }
}