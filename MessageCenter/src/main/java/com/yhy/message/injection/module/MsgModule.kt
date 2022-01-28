package com.yhy.message.injection.module

import com.yhy.message.service.MsgService
import com.yhy.message.service.impl.MsgServiceImpl
import dagger.Module
import dagger.Provides

@Module
class MsgModule {

    @Provides
    fun providesMsgService(service: MsgServiceImpl): MsgService = service
}