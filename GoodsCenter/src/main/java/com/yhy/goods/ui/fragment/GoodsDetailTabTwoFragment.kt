package com.yhy.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.yhy.base.ext.loadUrl
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.fragment.BaseFragment
import com.yhy.goods.R
import com.yhy.goods.event.GoodsDetailImageEvent
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_two.*

class GoodsDetailTabTwoFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_goods_detail_tab_two, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    /*
       初始化监听，商品详情获取成功后，加载当前页面
    */
    private fun initObserve() {
        Bus.observe<GoodsDetailImageEvent>()
            .subscribe {
                    t: GoodsDetailImageEvent ->
                run {
                    mGoodsDetailOneIv.loadUrl(t.imgOne)
                    mGoodsDetailTwoIv.loadUrl(t.imgTwo)
                }
            }
            .registerInBus(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}