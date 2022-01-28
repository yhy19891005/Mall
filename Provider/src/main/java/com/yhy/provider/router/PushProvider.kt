package com.yhy.provider.router

import com.alibaba.android.arouter.facade.template.IProvider

//ARouter跨模块接口调用
//步骤一: 自定义接口(PushProvider),继承ARouter中的IProvider接口
//步骤二: 见PushProviderImpl
//步骤三: 见调用的地方(LoginActivity)
interface PushProvider: IProvider {

    /**
     *  获取极光推送的 pushId
     */
    fun getPushId(): String
}