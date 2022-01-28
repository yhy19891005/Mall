package com.yhy.order.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yhy.order.common.OrderConstant
import com.yhy.order.ui.fragment.OrderFragment

class OrderVpAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val titles = arrayOf("全部", "待付款", "待收货", "已完成", "已取消")

    override fun getCount(): Int = titles.size

    override fun getItem(position: Int): Fragment {
        val fragment = OrderFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS, position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}