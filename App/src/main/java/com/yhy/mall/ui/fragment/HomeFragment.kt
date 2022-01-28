package com.yhy.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhy.base.ui.fragment.BaseFragment
import com.yhy.goods.ui.activity.SearchGoodsActivity
import com.yhy.mall.R
import com.yhy.mall.common.*
import com.yhy.base.data.protocol.BannerInfo
import com.yhy.mall.ui.adapter.DiscountAdapter
import com.yhy.base.ui.adapter.ImageAdapter
import com.yhy.mall.ui.adapter.TopicAdapter
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import me.crosswall.lib.coverflow.CoverFlow

class HomeFragment: BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, null)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSearchEt.setOnClickListener {
            startActivity(Intent(activity,SearchGoodsActivity::class.java))
        }
        initBanner(view)
        initNews()
        initDiscount()
        initTopic()
    }

    //广告图
    private fun initBanner(view: View) {
        val list = arrayListOf<BannerInfo>()
        list.add(BannerInfo(HOME_BANNER_ONE, "第一张广告图"))
        list.add(BannerInfo(HOME_BANNER_TWO, "第二张广告图"))
        list.add(BannerInfo(HOME_BANNER_THREE, "第三张广告图"))
        list.add(BannerInfo(HOME_BANNER_FOUR, "第四张广告图"))
        activity?.let {
            val adapter = ImageAdapter(it, list)
            val mHomeBanner =  view.findViewById<Banner<BannerInfo, ImageAdapter>>(R.id.mHomeBanner)
            mHomeBanner.setAdapter(adapter)
                .addBannerLifecycleObserver(this@HomeFragment)
            mHomeBanner.indicator = CircleIndicator(it)
            mHomeBanner.startPosition = 0
            mHomeBanner.start()
        }
    }

    //公告
    private fun initNews(){
        mNewsFlipperView.setData(arrayOf("夏日炎炎,第一波福利还有30秒到达战场", "新用户立领1000元优惠券", "推荐有礼,多推多得"))
    }

    //折扣
    private fun initDiscount(){
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        mHomeDiscountRv.layoutManager = manager

        activity?.let {
            val discountAdapter  = DiscountAdapter(it)
            mHomeDiscountRv.adapter = discountAdapter
            discountAdapter.setData(mutableListOf(HOME_DISCOUNT_ONE, HOME_DISCOUNT_TWO, HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE))
        }
    }

    /*
       初始化主题
    */
    private fun initTopic(){
        context?.let {
            //话题
            mTopicPager.adapter = TopicAdapter(it, listOf(HOME_TOPIC_ONE, HOME_TOPIC_TWO, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
            mTopicPager.currentItem = 1
            mTopicPager.offscreenPageLimit = 5

            CoverFlow.Builder().with(mTopicPager).scale(0.3f).pagerMargin(-30.0f).spaceSize(0.0f).build()
        }

    }
}