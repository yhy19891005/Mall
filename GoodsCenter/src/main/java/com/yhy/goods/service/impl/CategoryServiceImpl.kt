package com.yhy.goods.service.impl

import com.yhy.base.ext.covert
import com.yhy.goods.data.protocol.Category
import com.yhy.goods.data.repository.CategoryRepository
import com.yhy.goods.service.CategoryService
import rx.Observable
import javax.inject.Inject

class CategoryServiceImpl  @Inject constructor(): CategoryService {

    //CategoryRepository实例,dagger注入
    @Inject
    lateinit var repository: CategoryRepository

    /**
     * 获取商品分类列表
     * */
    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> = repository.getCategory(parentId).covert()

}