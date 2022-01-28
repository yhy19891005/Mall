package com.yhy.base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.yhy.base.injection.component.AppComponent
import com.yhy.base.injection.component.DaggerAppComponent
import com.yhy.base.injection.module.AppModule

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this

        initInjection()

        initARouter()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initARouter() {
        ARouter.init(this)
    }
}