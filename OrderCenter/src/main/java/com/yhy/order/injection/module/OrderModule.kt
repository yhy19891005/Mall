package com.yhy.order.injection.module

import com.yhy.order.service.OrderService
import com.yhy.order.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

@Module
class OrderModule {

    @Provides
    fun providesOrderService(service: OrderServiceImpl): OrderService = service

}