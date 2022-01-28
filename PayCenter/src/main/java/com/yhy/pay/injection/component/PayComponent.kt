package com.yhy.pay.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.pay.injection.module.PayModule
import com.yhy.pay.ui.activity.CashRegisterActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [PayModule::class])
interface PayComponent {

    fun inject(activity: CashRegisterActivity)
}