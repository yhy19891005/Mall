package com.yhy.order.ui.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.yhy.base.ui.activity.BaseActivity
import com.yhy.order.R
import com.yhy.order.common.OrderConstant
import com.yhy.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView() {
        val adapter = OrderVpAdapter(supportFragmentManager)
        mOrderVp.adapter = adapter
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderTab.setupWithViewPager(mOrderVp)

        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)
    }
}