package com.yhy.message.provider

import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.yhy.base.common.RouterPath
import com.yhy.provider.router.PushProvider

//ARouter跨模块接口调用
//步骤一: 见PushProvider
//步骤二: 自定义PushProvider接口实现类PushProviderImpl, 并配置path
//步骤三: 见调用的地方(LoginActivity)
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
class PushProviderImpl: PushProvider {

    private var mContext: Context? = null
    /**
     *  获取极光推送的 pushId
     */
    override fun getPushId(): String = JPushInterface.getRegistrationID(mContext)

    override fun init(context: Context?) {
        mContext = context
    }
}