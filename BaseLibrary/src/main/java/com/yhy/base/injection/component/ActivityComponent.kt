package com.yhy.base.injection.component

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.yhy.base.injection.ActivityScope
import com.yhy.base.injection.module.ActivityModule
import com.yhy.base.injection.module.LifecycleProviderModule
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class, LifecycleProviderModule::class])
interface ActivityComponent {

    fun context(): Context

    fun activity(): Activity

    fun lifecycleProvider(): LifecycleProvider<*>
}