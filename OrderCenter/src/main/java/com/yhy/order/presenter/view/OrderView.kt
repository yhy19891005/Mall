package com.yhy.order.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.order.data.protocol.Order

interface OrderView: BaseView {

    fun onGetOrderGoodsResult(result: Order)

    fun onSubmitOrderResult(result: Boolean)
}