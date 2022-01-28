package com.yhy.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.tabs.TabLayout
import com.yhy.base.ext.afterLogin
import com.yhy.base.ext.onClick
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.activity.BaseActivity
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.base.widget.badge.QBadgeView
import com.yhy.goods.R
import com.yhy.goods.common.SP_CART_SIZE
import com.yhy.goods.event.AddCartEvent
import com.yhy.goods.event.UpdateCartSizeEvent
import com.yhy.goods.ui.adapter.GoodsDetailVpAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetailActivity: BaseActivity() {

    lateinit var mQBadgeView: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        mQBadgeView = QBadgeView(this)

        mLeftIv.onClick { finish() }
        mAddCartBtn.onClick {
//            if(isLogin()){
//                Bus.send(AddCartEvent())
//            }else{
//                ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
//            }
            //抽取为kotlin扩展方法
            afterLogin {
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            startActivity(Intent(this,CartActivity::class.java))
        }
        
        val adapter = GoodsDetailVpAdapter(supportFragmentManager)
        mGoodsDetailVp.adapter = adapter
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        refreshCartSize()

        //添加商品至购物车后,同步更新页面数据
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {

                run {
                   refreshCartSize()
                }
            }
            .registerInBus(this)
    }

    private fun refreshCartSize(){
        val cartSize = AppPrefsUtils.getInt(SP_CART_SIZE)
        if(cartSize > 0){
            mQBadgeView.badgeGravity = Gravity.TOP or Gravity.END
            mQBadgeView.setGravityOffset(22F,-3F,true)
            mQBadgeView.setBadgeTextSize(10F,true)
            mQBadgeView.badgeNumber = cartSize
            mQBadgeView.bindTarget(mEnterCartTv)
        }else{
            mQBadgeView.hide(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}