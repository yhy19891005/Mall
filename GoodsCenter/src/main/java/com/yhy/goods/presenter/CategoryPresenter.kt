package com.yhy.goods.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.goods.data.protocol.Category
import com.yhy.goods.presenter.view.CategoryView
import com.yhy.goods.service.CategoryService
import javax.inject.Inject

class CategoryPresenter @Inject constructor(): BasePresenter<CategoryView>() {

    //dagger注入CategoryService实例
    @Inject
    lateinit var service: CategoryService

    fun getCategory(parentId: Int){

        if(!checkNet()) return
        mView.showLoading()

        service.getCategory(parentId)
            .excute(object : BaseSubscriber<MutableList<Category>?>(mView){
                override fun onNext(t: MutableList<Category>?) {
                    mView.onGetCategoryResult(t)
                }
            },provider)
    }
}


