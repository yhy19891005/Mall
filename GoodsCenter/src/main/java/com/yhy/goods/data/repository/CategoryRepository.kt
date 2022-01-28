package com.yhy.goods.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.goods.data.api.CategoryApi
import com.yhy.goods.data.protocol.Category
import com.yhy.goods.data.protocol.GetCategoryReq

import rx.Observable
import javax.inject.Inject

class CategoryRepository @Inject constructor(){

    /**
     * 获取商品分类列表
     * */
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>>{
       return RetrofitFactory.INSTANCE
                             .create(CategoryApi::class.java)
                             .getCategory(GetCategoryReq(parentId))
    }
}