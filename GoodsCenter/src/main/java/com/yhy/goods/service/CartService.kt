package com.yhy.goods.service

import com.yhy.goods.data.protocol.CartGoods
import rx.Observable

interface CartService {

    /**
     * 获取购物车列表
     * */
    fun getCartList(): Observable<MutableList<CartGoods>?>

    /**
     * 添加商品到购物车
    goodsId: Int, //商品ID
    goodsDesc: String, //商品描述
    goodsIcon: String, //商品图标
    goodsPrice: Long, //商品价格
    goodsCount: Int, //商品数量
    goodsSku: String //商品SKU
     * */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String,
                goodsPrice: Long, goodsCount: Int, goodsSku: String): Observable<Int>

    /**
     * 删除购物车商品
     * */
    fun deleteCartList(cartIdList: List<Int>): Observable<Boolean>

    /**
     * 提交购物车商品
     * */
    fun submitCart(goodsList: List<CartGoods>, totalPrice: Long): Observable<Int>
}