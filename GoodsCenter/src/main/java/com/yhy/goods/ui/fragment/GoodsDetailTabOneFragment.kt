package com.yhy.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.yhy.base.data.protocol.BannerInfo
import com.yhy.base.ext.onClick
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.adapter.ImageAdapter
import com.yhy.base.ui.fragment.BaseMvpFragment
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.base.utils.YuanFenConverter
import com.yhy.goods.R
import com.yhy.goods.common.GOODS_ID
import com.yhy.goods.common.SKU_SEPARATOR
import com.yhy.goods.common.SP_CART_SIZE
import com.yhy.goods.data.protocol.Goods
import com.yhy.goods.event.AddCartEvent
import com.yhy.goods.event.GoodsDetailImageEvent
import com.yhy.goods.event.SkuChangedEvent
import com.yhy.goods.event.UpdateCartSizeEvent
import com.yhy.goods.injection.component.DaggerGoodsComponent
import com.yhy.goods.injection.module.GoodsModule
import com.yhy.goods.presenter.GoodsDetailPresenter
import com.yhy.goods.presenter.view.GoodsDetailView
import com.yhy.goods.widget.GoodsSkuPopView
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.mSkuView
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_two.*
import kotlinx.android.synthetic.main.layout_sku_pop.*

class GoodsDetailTabOneFragment: BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {

    private lateinit var mPop: GoodsSkuPopView
    private lateinit var mView: View

    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation
    //当前选中的商品
    private var currentGoods: Goods? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mView = inflater.inflate(R.layout.fragment_goods_detail_tab_one, null)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val goodId = it.intent.getIntExtra(GOODS_ID,-1)
            mPresenter.getGoodsDetail(goodId)
        }

        initAnim()

        initObserve()
    }

    /*
  初始化缩放动画
*/
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder()
            .activityComponent(activityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetGoodsDetailResult(result: Goods) {
        currentGoods = result
        initBanner(result)
        initView(result)
        initPopData(result)
    }

    override fun onAddCartResult(result: Int) {
        AppPrefsUtils.putInt(SP_CART_SIZE, result)
        Bus.send(UpdateCartSizeEvent())
    }

    private fun initBanner(result: Goods) {
        val imgList = result.goodsBanner.split(SKU_SEPARATOR)
        val list = arrayListOf<BannerInfo>()
        for(str in imgList){
            list.add(BannerInfo(str,""))
        }
        activity?.let {
            val adapter = ImageAdapter(it, list)
            val mBanner =  mView.findViewById<Banner<BannerInfo, ImageAdapter>>(R.id.mGoodsDetailBanner)
            mBanner.setAdapter(adapter)
                .addBannerLifecycleObserver(this@GoodsDetailTabOneFragment)
            mBanner.indicator = CircleIndicator(it)
            mBanner.startPosition = 0
            mBanner.start()
        }
    }

    private fun initView(result: Goods){
        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv1.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        Bus.send(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))
    }

    private fun initPopData(result: Goods){
        activity?.let {
            mPop = GoodsSkuPopView(it)
            mPop.setGoodsIcon(result.goodsDefaultIcon)
            mPop.setGoodsCode(result.goodsCode)
            mPop.setGoodsPrice(result.goodsDefaultPrice)

            mPop.setSkuData(result.goodsSku)

            mPop.setOnDismissListener { mView.startAnimation(mAnimationEnd) }

            mSkuView.onClick {
                mPop.showAsDropDown(mView)
                mView.startAnimation(mAnimationStart)
            }
        }
    }

    /*
      初始化监听
   */
    private fun initObserve(){
        //商品SKU变化后,同步更新页面数据
        Bus.observe<SkuChangedEvent>()
            .subscribe {

            run {
                mSkuSelectedTv.text = mPop.getSelectSku() + SKU_SEPARATOR + mPop.getSelectCount() + "件"
                }
            }
            .registerInBus(this)

        //添加商品到购物车
        Bus.observe<AddCartEvent>()
            .subscribe {

                run {
                   addCart()
                }
            }
            .registerInBus(this)
    }

    private fun addCart() {
        currentGoods?.let {
            mPresenter.addCart(it.id, it.goodsDesc, it.goodsDefaultIcon, it.goodsDefaultPrice, mPop.getSelectCount(), mPop.getSelectSku())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}