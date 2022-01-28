package com.yhy.order.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.order.data.protocol.Order

interface OrderDetailView: BaseView {

    fun onGetOrderDetailResult(result: Order)
}