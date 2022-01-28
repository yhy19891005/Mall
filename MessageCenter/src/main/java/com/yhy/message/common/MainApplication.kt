package com.yhy.message.common

import cn.jpush.android.api.JPushInterface
import com.yhy.base.common.BaseApplication

class MainApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

    }
}