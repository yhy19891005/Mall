package com.yhy.goods.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.goods.data.protocol.CartGoods
import com.yhy.goods.presenter.view.CartView
import com.yhy.goods.service.CartService
import javax.inject.Inject

class CartPresenter @Inject constructor(): BasePresenter<CartView>() {

    //dagger注入CartService实例
    @Inject
    lateinit var service: CartService

    /**
     *  获取购物车列表
     * */
    fun getCartList(){

        if(!checkNet()) return
        mView.showLoading()

        service.getCartList()
            .excute(object : BaseSubscriber<MutableList<CartGoods>?>(mView){
                override fun onNext(t: MutableList<CartGoods>?) {
                    mView.onGetCartGoodsResult(t)
                }
            },provider)
    }

    /**
     *  删除购物车列表中的商品
     * */
    fun deleteCartGoods(cartIdList: List<Int>){

        if(!checkNet()) return
        mView.showLoading()

        service.deleteCartList(cartIdList)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onDeleteCartGoodsResult(t)
                }
            },provider)
    }

    /**
     *  提交购物车列表中的商品
     * */
    fun submitCartGoods(goodsList: List<CartGoods>, totalPrice: Long){

        if(!checkNet()) return
        mView.showLoading()

        service.submitCart(goodsList, totalPrice)
            .excute(object : BaseSubscriber<Int>(mView){
                override fun onNext(t: Int) {
                    mView.onSubmitCartGoodsResult(t)
                }
            },provider)
    }
}