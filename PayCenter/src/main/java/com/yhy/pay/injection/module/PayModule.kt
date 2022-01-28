package com.yhy.pay.injection.module

import com.yhy.pay.service.PayService
import com.yhy.pay.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

@Module
class PayModule {

    @Provides
    fun providesPayService(service: PayServiceImpl): PayService = service
}