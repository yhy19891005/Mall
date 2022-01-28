package com.yhy.pay.service.impl

import com.yhy.base.ext.covert
import com.yhy.base.ext.covertBoolean
import com.yhy.pay.data.repository.PayRepository
import com.yhy.pay.service.PayService
import rx.Observable
import javax.inject.Inject

class PayServiceImpl  @Inject constructor(): PayService {

    //PayRepository实例,dagger注入
    @Inject
    lateinit var repository: PayRepository

    /**
     * 获取支付宝支付签名
     * */
    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> = repository.getPaySign(orderId, totalPrice).covert()

    /**
     * 刷新订单状态，已支付
     * */
    override fun payOrder(orderId: Int): Observable<Boolean> = repository.payOrder(orderId).covertBoolean()
}