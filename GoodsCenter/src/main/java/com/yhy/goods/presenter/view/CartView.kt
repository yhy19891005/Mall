package com.yhy.goods.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.goods.data.protocol.CartGoods

interface CartView: BaseView {

    fun onGetCartGoodsResult(result: MutableList<CartGoods>?)

    fun onDeleteCartGoodsResult(result: Boolean)

    fun onSubmitCartGoodsResult(result: Int)
}