package com.yhy.user.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.user.data.protocol.UserInfo

interface ForgetPwdView: BaseView {

    fun onForgetPwdResult(result: Boolean)
}