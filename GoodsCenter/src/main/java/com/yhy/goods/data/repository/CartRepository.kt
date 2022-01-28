package com.yhy.goods.data.repository

import com.kotlin.goods.data.protocol.AddCartReq
import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.goods.data.api.CartApi
import com.yhy.goods.data.protocol.CartGoods
import com.yhy.goods.data.protocol.DeleteCartReq
import com.yhy.goods.data.protocol.SubmitCartReq
import rx.Observable
import javax.inject.Inject

class CartRepository @Inject constructor(){

    /**
     * 获取购物车列表
     * */
    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>> {
        return RetrofitFactory.INSTANCE
            .create(CartApi::class.java)
            .getCartList()
    }

    /**
     * 添加商品到购物车
     * goodsId: Int, //商品ID
       goodsDesc: String, //商品描述
       goodsIcon: String, //商品图标
       goodsPrice: Long, //商品价格
       goodsCount: Int, //商品数量
       goodsSku: String //商品SKU
     * */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String,
                goodsPrice: Long, goodsCount: Int, goodsSku: String): Observable<BaseResp<Int>> {
        return RetrofitFactory.INSTANCE
            .create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku))
    }

    /**
     * 删除购物车商品
     * */
    fun deleteCartList(cartIdList: List<Int>): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE
            .create(CartApi::class.java)
            .deleteCartList(DeleteCartReq(cartIdList))
    }

    /**
     * 提交购物车商品
     * */
    fun submitCart(goodsList: List<CartGoods>, totalPrice: Long): Observable<BaseResp<Int>> {
        return RetrofitFactory.INSTANCE
            .create(CartApi::class.java)
            .submitCart(SubmitCartReq(goodsList, totalPrice))
    }
}