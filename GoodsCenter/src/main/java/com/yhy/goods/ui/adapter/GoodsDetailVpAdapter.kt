package com.yhy.goods.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yhy.goods.ui.fragment.GoodsDetailTabOneFragment
import com.yhy.goods.ui.fragment.GoodsDetailTabTwoFragment

class GoodsDetailVpAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val titles = arrayOf("商品","详情")

    override fun getCount(): Int = titles.size

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            GoodsDetailTabOneFragment()
        } else {
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}