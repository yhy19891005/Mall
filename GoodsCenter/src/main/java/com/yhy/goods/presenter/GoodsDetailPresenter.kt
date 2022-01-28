package com.yhy.goods.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.goods.data.protocol.Goods
import com.yhy.goods.presenter.view.GoodsDetailView
import com.yhy.goods.service.CartService
import com.yhy.goods.service.GoodsService
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor(): BasePresenter<GoodsDetailView>() {

    //dagger注入GoodsService实例
    @Inject
    lateinit var service: GoodsService

    //dagger注入GoodsService实例
    @Inject
    lateinit var cartService: CartService

    /**
     * 获取商品详情
     * */
    fun getGoodsDetail(goodsId: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getGoodsDetail(goodsId)
            .excute(object : BaseSubscriber<Goods>(mView){
                override fun onNext(t: Goods) {
                    mView.onGetGoodsDetailResult(t)
                }
            },provider)
    }

    /**
     * 添加商品到购物车
     * */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String,
                goodsPrice: Long, goodsCount: Int, goodsSku: String){

        if(!checkNet()) return
        mView.showLoading()

        cartService.addCart(goodsId,goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .excute(object : BaseSubscriber<Int>(mView){
                override fun onNext(t: Int) {
                    mView.onAddCartResult(t)
                }
            },provider)
    }
}


