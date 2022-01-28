package com.yhy.goods.injection.module

import com.yhy.goods.service.CartService
import com.yhy.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

@Module
class CartModule {

    @Provides
    fun providesCartService(service: CartServiceImpl): CartService = service

}