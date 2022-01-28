package com.yhy.goods.service

import com.yhy.goods.data.protocol.Category
import rx.Observable

interface CategoryService {

    /**
     * 获取商品分类列表
     * */
    fun getCategory(parentId: Int): Observable<MutableList<Category>?>

}