package com.yhy.base.injection.module

import android.content.Context
import com.yhy.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun providesContext(): Context = context
}