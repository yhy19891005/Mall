package com.yhy.user.injection.component

import com.yhy.base.injection.PerComponentScope
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.user.injection.module.UpLoadModule
import com.yhy.user.injection.module.UserModule
import com.yhy.user.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = [ActivityComponent::class], modules = [UserModule::class, UpLoadModule::class])
interface UserComponent {

    fun inject(activity: RegisterActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: ForgetPwdActivity)

    fun inject(activity: ResetPwdActivity)

    fun inject(activity: UserInfoActivity)
}