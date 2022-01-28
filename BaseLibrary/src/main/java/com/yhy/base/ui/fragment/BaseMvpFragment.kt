package com.yhy.base.ui.fragment

import android.os.Bundle
import android.widget.Toast
import com.yhy.base.common.BaseApplication
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.base.injection.component.DaggerActivityComponent
import com.yhy.base.injection.module.ActivityModule
import com.yhy.base.injection.module.LifecycleProviderModule
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.presenter.view.BaseView
import javax.inject.Inject

open abstract class BaseMvpFragment<T: BasePresenter<*>>: BaseFragment(),BaseView {

    //Presenter泛型, Dagger注入
    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent
                            .builder()
                            .appComponent((activity?.application as BaseApplication).appComponent)
                            .activityModule(ActivityModule(requireActivity()))
                            .lifecycleProviderModule(LifecycleProviderModule(this))
                            .build()

        injectComponent()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun injectComponent()
}