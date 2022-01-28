package com.yhy.goods.data.api

import com.kotlin.goods.data.protocol.*
import com.yhy.base.data.protocol.BaseResp
import com.yhy.goods.data.protocol.GetGoodsDetailReq
import com.yhy.goods.data.protocol.GetGoodsListByKeywordReq
import com.yhy.goods.data.protocol.GetGoodsListReq
import com.yhy.goods.data.protocol.Goods
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品接口
 */
interface GoodsApi {
    /*
        获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        根据关键字搜索,获取商品列表
     */
    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品详情
     */
    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
