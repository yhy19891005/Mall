package com.yhy.goods.ui.activity

import android.os.Bundle
import com.yhy.base.ui.activity.BaseActivity
import com.yhy.goods.R
import com.yhy.goods.ui.fragment.CartFragment

class CartActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val cartFragment = supportFragmentManager.findFragmentById(R.id.fragment_cart) as CartFragment
        cartFragment.showLeftIv()
    }
}