package com.yhy.goods.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.goods.injection.module.CartModule
import com.yhy.goods.injection.module.GoodsModule
import com.yhy.goods.ui.activity.GoodsActivity
import com.yhy.goods.ui.activity.KeyWordGoodsActivity
import com.yhy.goods.ui.fragment.CartFragment
import com.yhy.goods.ui.fragment.GoodsDetailTabOneFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [CartModule::class])
interface CartComponent {

    fun inject(fragment: CartFragment)
}