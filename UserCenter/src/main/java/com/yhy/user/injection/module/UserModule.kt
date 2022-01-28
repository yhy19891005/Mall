package com.yhy.user.injection.module

import com.yhy.user.service.UserService
import com.yhy.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): UserService = service

}