package com.yhy.goods.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.goods.data.protocol.Category
import com.yhy.goods.data.protocol.Goods
import com.yhy.goods.presenter.view.GoodsView
import com.yhy.goods.service.GoodsService
import javax.inject.Inject

class GoodsPresenter @Inject constructor(): BasePresenter<GoodsView>() {

    //dagger注入UserService实例
    @Inject
    lateinit var service: GoodsService

    /**
     * 获取商品分类列表
     * */
    fun getGoodsList(categoryId: Int, pageNo: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getGoodsList(categoryId, pageNo)
            .excute(object : BaseSubscriber<MutableList<Goods>?>(mView){
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsResult(t)
                }
            },provider)
    }

    /**
     * 根据关键字获取商品分类列表
     * */
    fun getGoodsListByKeyWord(keyWord: String, pageNo: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getGoodsListByKeyWord(keyWord, pageNo)
            .excute(object : BaseSubscriber<MutableList<Goods>?>(mView){
                override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsResult(t)
                }
            },provider)
    }
}


