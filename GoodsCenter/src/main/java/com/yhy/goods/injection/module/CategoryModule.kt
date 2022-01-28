package com.yhy.goods.injection.module

import com.yhy.goods.service.CategoryService
import com.yhy.goods.service.impl.CategoryServiceImpl
import dagger.Module
import dagger.Provides

@Module
class CategoryModule {

    @Provides
    fun providesCategoryService(service: CategoryServiceImpl): CategoryService = service

}