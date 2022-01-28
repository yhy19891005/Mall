package com.yhy.mall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.yhy.base.common.AppManager
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.goods.common.SP_CART_SIZE
import com.yhy.goods.event.UpdateCartSizeEvent
import com.yhy.goods.ui.fragment.CategoryFragment
import com.yhy.goods.ui.fragment.CartFragment
import com.yhy.mall.R
import com.yhy.mall.ui.fragment.HomeFragment
import com.yhy.mall.ui.fragment.MeFragment
import com.yhy.message.event.MessageBridgeEvent
import com.yhy.message.ui.fragment.MsgFragment
import com.yhy.provider.event.LoginSucEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val stack = Stack<Fragment>()
    private var pressTime = 0L

    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMsgFragment by lazy { MsgFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initNavBar()
        changeFragment(0)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.content, HomeFragment())
//        transaction.commit()
        refreshCartSize()
        initObserve()

    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content, mHomeFragment)
        transaction.add(R.id.content, mCategoryFragment)
        transaction.add(R.id.content, mCartFragment)
        transaction.add(R.id.content, mMsgFragment)
        transaction.add(R.id.content, mMeFragment)
        transaction.commit()

        stack.add(mHomeFragment)
        stack.add(mCategoryFragment)
        stack.add(mCartFragment)
        stack.add(mMsgFragment)
        stack.add(mMeFragment)

    }

    private fun initNavBar() {
        mBottomNavBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener{
            override fun onTabSelected(position: Int) {
               changeFragment(position)
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {

            }

        })
        mBottomNavBar.checkMsgBadge(false)
    }

    private fun changeFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        for (fragment in stack){
            transaction.hide(fragment)
        }
        transaction.show(stack[position])
        transaction.commit()
    }

    private fun initObserve(){
        //添加商品至购物车后,同步更新页面数据
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {

                run {
                    refreshCartSize()
                }
            }
            .registerInBus(this)

        //有新消息时,展示红点
        Bus.observe<MessageBridgeEvent>()
            .subscribe {
                t: MessageBridgeEvent ->
                run {
                    mBottomNavBar.checkMsgBadge(t.isShowBridge)
                }
            }
            .registerInBus(this)

        Bus.observe<LoginSucEvent>()
            .subscribe {

                run {
                    mBottomNavBar.checkMsgBadge(false)
                }
            }
            .registerInBus(this)
    }

    private fun refreshCartSize() {
        val cartSize = AppPrefsUtils.getInt(SP_CART_SIZE)
        mBottomNavBar.checkCartBadge(cartSize)
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if(time - pressTime > 2000){
            Toast.makeText(this,"再按一次退出应用", Toast.LENGTH_SHORT).show()
            pressTime = time
        }else{
            AppManager.INSTANCE.exitApp(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}