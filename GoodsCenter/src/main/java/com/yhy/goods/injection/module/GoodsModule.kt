package com.yhy.goods.injection.module

import com.yhy.goods.service.GoodsService
import com.yhy.goods.service.impl.GoodsServiceImpl
import dagger.Module
import dagger.Provides

@Module
class GoodsModule {

    @Provides
    fun providesGoodsService(service: GoodsServiceImpl): GoodsService = service

}