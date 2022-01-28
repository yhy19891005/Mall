package com.yhy.base.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.yhy.base.common.AppManager

open class BaseActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.INSTANCE.addActivity(this)
        //ARouter方式获取Intent数据 步骤二 注册 (也是跨模块接口调用的第四步)
        ARouter.getInstance().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.INSTANCE.finishActivity(this)
    }
}