package com.yhy.goods.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.goods.data.api.CategoryApi
import com.yhy.goods.data.api.GoodsApi
import com.yhy.goods.data.protocol.*

import rx.Observable
import javax.inject.Inject

class GoodsRepository @Inject constructor(){

    /**
     * 获取商品分类列表
     * */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
       return RetrofitFactory.INSTANCE
                             .create(GoodsApi::class.java)
                             .getGoodsList(GetGoodsListReq(categoryId,pageNo))
    }

    /**
     * 根据关键字获取商品分类列表
     * */
    fun getGoodsListByKeyWord(keyWord: String, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.INSTANCE
            .create(GoodsApi::class.java)
            .getGoodsListByKeyword(GetGoodsListByKeywordReq(keyWord,pageNo))
    }

    /**
     * 获取商品详情
     * */
    fun getGoodsDetail(goodsId: Int): Observable<BaseResp<Goods>> {
        return RetrofitFactory.INSTANCE
            .create(GoodsApi::class.java)
            .getGoodsDetail(GetGoodsDetailReq(goodsId))
    }
}