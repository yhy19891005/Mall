package com.yhy.order.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.order.data.protocol.Order

interface StatusOrderView: BaseView {

    fun onGetStatusOrderResult(result: MutableList<Order>?)

    fun onConfirmOrderResult(result: Boolean)

    fun onCancelOrderResult(result: Boolean)

}