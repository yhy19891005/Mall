package com.yhy.order.service

import com.yhy.order.data.protocol.*
import rx.Observable

interface OrderService {

    /**
     * 取消订单
     * */
    fun cancelOrder(orderId:Int): Observable<Boolean>

    /**
     * 确认订单
     * */
    fun confirmOrder(orderId:Int): Observable<Boolean>

    /**
     * 根据ID获取订单
     * */
    fun getOrderById(orderId: Int): Observable<Order>

    /**
     * 根据订单状态查询查询订单列表
     * */
    fun getOrderList(orderStatus:Int): Observable<MutableList<Order>?>

    /**
     * 提交订单
     * */
    fun submitOrder(order: Order): Observable<Boolean>
}