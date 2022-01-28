package com.yhy.user.presenter.view

import com.yhy.base.presenter.view.BaseView

interface RegisterView: BaseView {

    fun onRegisterResult(result: String)
}