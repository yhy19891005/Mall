package com.yhy.goods.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.goods.data.protocol.Goods

interface GoodsView: BaseView {

    fun onGetGoodsResult(result: MutableList<Goods>?)
}