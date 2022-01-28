package com.yhy.order.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.order.data.api.OrderApi
import com.yhy.order.data.protocol.*
import rx.Observable
import javax.inject.Inject

class OrderRepository @Inject constructor() {

    /**
     * 取消订单
     * */
    fun cancelOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE
            .create(OrderApi::class.java)
            .cancelOrder(CancelOrderReq(orderId))
    }

    /**
     * 确认订单
     * */
    fun confirmOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE
            .create(OrderApi::class.java)
            .confirmOrder(ConfirmOrderReq(orderId))
    }

    /**
     * 根据ID获取订单
     * */
    fun getOrderById(orderId: Int): Observable<BaseResp<Order>> {
        return RetrofitFactory.INSTANCE
            .create(OrderApi::class.java)
            .getOrderById(GetOrderByIdReq(orderId))
    }

    /**
     * 根据订单状态查询查询订单列表
     * */
    fun getOrderList(orderStatus: Int): Observable<BaseResp<MutableList<Order>?>> {
        return RetrofitFactory.INSTANCE
            .create(OrderApi::class.java)
            .getOrderList(GetOrderListReq(orderStatus))
    }

    /**
     * 提交订单
     * */
    fun submitOrder(order: Order): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE
            .create(OrderApi::class.java)
            .submitOrder(SubmitOrderReq(order))
    }
}