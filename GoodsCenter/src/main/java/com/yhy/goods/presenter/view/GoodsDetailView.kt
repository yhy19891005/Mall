package com.yhy.goods.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.goods.data.protocol.Goods

interface GoodsDetailView: BaseView {

    fun onGetGoodsDetailResult(result: Goods)

    fun onAddCartResult(result: Int)
}