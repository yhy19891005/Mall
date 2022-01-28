package com.yhy.order.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.order.injection.module.AddressModule
import com.yhy.order.ui.activity.AddressEditActivity
import com.yhy.order.ui.activity.AddressListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [AddressModule::class])
interface AddressComponent {

    fun inject(activity: AddressEditActivity)

    fun inject(activity: AddressListActivity)
}