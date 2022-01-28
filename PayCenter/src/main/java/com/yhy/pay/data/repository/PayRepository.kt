package com.yhy.pay.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.pay.data.api.PayApi
import com.yhy.pay.data.protocol.GetPaySignReq
import com.yhy.pay.data.protocol.PayOrderReq
import rx.Observable
import javax.inject.Inject

class PayRepository @Inject constructor() {

    /**
     * 获取支付宝支付签名
     * */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(PayApi::class.java)
            .getPaySign(GetPaySignReq(orderId, totalPrice))
    }

    /**
     * 刷新订单状态，已支付
     * */
    fun payOrder(orderId:Int): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(PayApi::class.java)
            .payOrder(PayOrderReq(orderId))
    }
}