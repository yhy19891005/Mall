package com.yhy.message.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.message.injection.module.MsgModule
import com.yhy.message.ui.fragment.MsgFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [MsgModule::class])
interface MsgComponent {

    fun inject(fragment: MsgFragment)
}