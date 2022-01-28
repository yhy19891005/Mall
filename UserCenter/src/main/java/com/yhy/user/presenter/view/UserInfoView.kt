package com.yhy.user.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.user.data.protocol.UserInfo

interface UserInfoView: BaseView {

   /**
    * 获取七牛云token成功时的回调
    * */
   fun onGetUpLoadTokenResult(result: String)

   /**
    * 编辑用户资料成功时的回调
    * */
   fun onEditUserResult(result: UserInfo)
}