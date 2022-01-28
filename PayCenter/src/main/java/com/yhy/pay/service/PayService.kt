package com.yhy.pay.service

import rx.Observable

interface PayService{

    /**
     * 获取支付宝支付签名
     * */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String>

    /**
     * 刷新订单状态，已支付
     * */
    fun payOrder(orderId:Int): Observable<Boolean>
}