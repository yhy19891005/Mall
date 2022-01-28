package com.yhy.user.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.user.data.protocol.UserInfo

interface LoginView: BaseView {

    fun onLoginResult(result: UserInfo)
}