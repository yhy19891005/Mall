package com.yhy.goods.service

import com.yhy.goods.data.protocol.Goods
import rx.Observable

interface GoodsService {

    /**
     * 获取商品分类列表
     * */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>

    /**
     * 根据关键字获取商品分类列表
     * */
    fun getGoodsListByKeyWord(keyWord: String, pageNo: Int): Observable<MutableList<Goods>?>

    /**
     * 获取商品详情
     * */
    fun getGoodsDetail(goodsId: Int): Observable<Goods>

}