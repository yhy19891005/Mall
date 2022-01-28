package com.yhy.order.injection.module

import com.yhy.order.service.AddressService
import com.yhy.order.service.impl.AddressServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AddressModule {

    @Provides
    fun providesAddressService(service: AddressServiceImpl): AddressService = service

}