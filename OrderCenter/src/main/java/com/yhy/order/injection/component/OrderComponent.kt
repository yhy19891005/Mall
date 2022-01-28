package com.yhy.order.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.order.injection.module.OrderModule
import com.yhy.order.ui.activity.OrderConfirmActivity
import com.yhy.order.ui.activity.OrderDetailActivity
import com.yhy.order.ui.fragment.OrderFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [OrderModule::class])
interface OrderComponent {

    fun inject(activity: OrderConfirmActivity)

    fun inject(fragment: OrderFragment)

    fun inject(activity: OrderDetailActivity)
}