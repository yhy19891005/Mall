package com.yhy.goods.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.goods.injection.module.CategoryModule
import com.yhy.goods.ui.fragment.CategoryFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CategoryModule::class])
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)

}