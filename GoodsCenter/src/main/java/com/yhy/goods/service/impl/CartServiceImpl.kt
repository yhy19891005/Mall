package com.yhy.goods.service.impl

import com.yhy.base.ext.covert
import com.yhy.base.ext.covertBoolean
import com.yhy.goods.data.protocol.CartGoods
import com.yhy.goods.data.repository.CartRepository
import com.yhy.goods.service.CartService
import rx.Observable
import javax.inject.Inject

class CartServiceImpl @Inject constructor(): CartService {

    //CartRepository实例,dagger注入
    @Inject
    lateinit var repository: CartRepository

    /**
     * 获取购物车列表
     * */
    override fun getCartList(): Observable<MutableList<CartGoods>?> = repository.getCartList().covert()

    /**
     * 添加商品到购物车
    goodsId: Int, //商品ID
    goodsDesc: String, //商品描述
    goodsIcon: String, //商品图标
    goodsPrice: Long, //商品价格
    goodsCount: Int, //商品数量
    goodsSku: String //商品SKU
     * */
    override fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int> = repository.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku).covert()

    /**
     * 删除购物车商品
     * */
    override fun deleteCartList(cartIdList: List<Int>): Observable<Boolean> = repository.deleteCartList(cartIdList).covertBoolean()

    /**
     * 提交购物车商品
     * */
    override fun submitCart(goodsList: List<CartGoods>, totalPrice: Long): Observable<Int> = repository.submitCart(goodsList, totalPrice).covert()


}