package com.yhy.goods.service.impl

import com.yhy.base.data.protocol.BaseResp
import com.yhy.base.ext.covert
import com.yhy.goods.data.protocol.Goods
import com.yhy.goods.data.repository.GoodsRepository
import com.yhy.goods.service.GoodsService
import rx.Observable
import javax.inject.Inject

class GoodsServiceImpl  @Inject constructor(): GoodsService {

    //GoodsRepository实例,dagger注入
    @Inject
    lateinit var repository: GoodsRepository

    /**
     * 获取商品分类列表
     * */
    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> = repository.getGoodsList(categoryId, pageNo).covert()

    /**
     * 根据关键字获取商品分类列表
     * */
    override fun getGoodsListByKeyWord(keyWord: String, pageNo: Int): Observable<MutableList<Goods>?> = repository.getGoodsListByKeyWord(keyWord, pageNo).covert()

    /**
     * 获取商品详情
     * */
    override fun getGoodsDetail(goodsId: Int): Observable<Goods> = repository.getGoodsDetail(goodsId).covert()
}