package com.yhy.base.injection.module

import android.app.Activity
import com.yhy.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun providesActivity() = activity
}