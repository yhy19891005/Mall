package com.yhy.base.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.yhy.base.common.BaseApplication
import com.yhy.base.injection.component.ActivityComponent
import com.yhy.base.injection.component.DaggerActivityComponent
import com.yhy.base.injection.module.ActivityModule
import com.yhy.base.injection.module.LifecycleProviderModule
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.presenter.view.BaseView
import com.yhy.base.widget.ProgressLoading
import javax.inject.Inject

open abstract class BaseMvpActivity<T: BasePresenter<*>>: BaseActivity(),BaseView {

    //Presenter泛型, Dagger注入
    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent
                            .builder()
                            .appComponent((application as BaseApplication).appComponent)
                            .activityModule(ActivityModule(this))
                            .lifecycleProviderModule(LifecycleProviderModule(this))
                            .build()

        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
    }

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun injectComponent()
}