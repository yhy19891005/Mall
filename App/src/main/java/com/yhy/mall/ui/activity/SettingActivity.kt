package com.yhy.mall.ui.activity

import android.os.Bundle
import android.view.View
import com.yhy.base.ext.isLogin
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseActivity
import com.yhy.mall.R
import com.yhy.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        if(isLogin()){
            mLogoutBtn.visibility = View.VISIBLE
        }else{
            mLogoutBtn.visibility = View.GONE
        }
        mLogoutBtn.onClick {
            //退出登录时,清除用户数据
            UserPrefsUtils.putUserInfo(null)
            finish()
        }
    }
}