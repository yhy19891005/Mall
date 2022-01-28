package com.yhy.base.injection.module

import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Module
import dagger.Provides

@Module
class LifecycleProviderModule(private val provider: LifecycleProvider<*>) {

    @Provides
    fun providesLifecycleProvider(): LifecycleProvider<*> = provider
}