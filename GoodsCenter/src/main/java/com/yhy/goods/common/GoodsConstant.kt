package com.yhy.goods.common

import android.widget.EditText
import com.yhy.goods.R
import com.yhy.goods.widget.NumberButton


/*
 * CategoryFragment传id给GoodsActivity时的key
 */
const val GOODS_ID = "goods_id"

const val GOODS_KEY_WORD = "goods_key_word"

/**
 * 商品搜索记录的关键字
 * */
const val SP_SEARCH_HISTORY = "sp_search_history"

//sku分隔标识
const val SKU_SEPARATOR = ","
//购物车数量
const val SP_CART_SIZE = "cart_size"

/*
    三方控件扩展
 */
fun NumberButton.getEditText(): EditText {
    return this.findViewById(R.id.text_count)
}


