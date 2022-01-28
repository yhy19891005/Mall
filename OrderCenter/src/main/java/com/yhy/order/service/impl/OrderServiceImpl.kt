package com.yhy.order.service.impl

import com.yhy.base.ext.covert
import com.yhy.base.ext.covertBoolean
import com.yhy.order.data.protocol.Order
import com.yhy.order.data.repository.OrderRepository
import com.yhy.order.service.OrderService
import rx.Observable
import javax.inject.Inject

class OrderServiceImpl @Inject constructor(): OrderService {

    //OrderRepository实例,dagger注入
    @Inject
    lateinit var repository: OrderRepository

    /**
     * 取消订单
     * */
    override fun cancelOrder(orderId: Int): Observable<Boolean> = repository.cancelOrder(orderId).covertBoolean()

    /**
     * 确认订单
     * */
    override fun confirmOrder(orderId: Int): Observable<Boolean> = repository.confirmOrder(orderId).covertBoolean()

    /**
     * 根据ID获取订单
     * */
    override fun getOrderById(orderId: Int): Observable<Order>  = repository.getOrderById(orderId).covert()

    /**
     * 根据订单状态查询查询订单列表
     * */
    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> = repository.getOrderList(orderStatus).covert()

    /**
     * 提交订单
     * */
    override fun submitOrder(order: Order): Observable<Boolean> = repository.submitOrder(order).covertBoolean()
}