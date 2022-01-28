package com.yhy.user.injection.module

import com.yhy.user.service.UpLoadService
import com.yhy.user.service.impl.UpLoadServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UpLoadModule {

    @Provides
    fun providesUpLoadService(service: UpLoadServiceImpl): UpLoadService = service

}