package com.yhy.goods.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.goods.data.protocol.Category

interface CategoryView: BaseView {

    fun onGetCategoryResult(result: MutableList<Category>?)
}