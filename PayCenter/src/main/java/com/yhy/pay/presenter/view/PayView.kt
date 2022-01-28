package com.yhy.pay.presenter.view

import com.yhy.base.presenter.view.BaseView

interface PayView: BaseView {

    fun onGetPaySignResult(result: String)

    fun onPayOrderResult(result: Boolean)
}