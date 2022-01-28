package com.yhy.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.ext.afterLogin
import com.yhy.base.ext.isLogin
import com.yhy.base.ext.loadUrl
import com.yhy.base.ext.onClick
import com.yhy.base.ui.fragment.BaseFragment
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.mall.R
import com.yhy.mall.ui.activity.SettingActivity
import com.yhy.order.common.OrderConstant
import com.yhy.order.common.OrderConstant.Companion.KEY_FROM_ME
import com.yhy.order.common.OrderStatus
import com.yhy.order.ui.activity.AddressListActivity
import com.yhy.order.ui.activity.OrderActivity
import com.yhy.user.ui.activity.LoginActivity
import com.yhy.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment: BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserIconIv.onClick { toOther() }
        mUserNameTv.onClick { toOther() }
        mSettingTv.onClick { startActivity(Intent(context, SettingActivity::class.java)) }
        mAddressTv.onClick {
            afterLogin {
                val intent = Intent(context, AddressListActivity::class.java)
                intent.putExtra(KEY_FROM_ME, true)
                startActivity(intent)
            }
        }

        mWaitPayOrderTv.onClick {
            afterLogin {
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_PAY)
                startActivity(intent)
            }
        }
        mWaitConfirmOrderTv.onClick {
            afterLogin {
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_CONFIRM)
                startActivity(intent)
            }
        }
        mCompleteOrderTv.onClick {
            afterLogin {
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_COMPLETED)
                startActivity(intent)
            }
        }
        mAllOrderTv.onClick {
            afterLogin {
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_ALL)
                startActivity(intent)
            }
        }
    }

    private fun toOther(){
        afterLogin {
            startActivity(Intent(context,UserInfoActivity::class.java))
        }
//        if(isLogin()){
//            startActivity(Intent(context,UserInfoActivity::class.java))
//        }else {
//            startActivity(Intent(context,LoginActivity::class.java))
//        }
    }

    override fun onStart() {
        super.onStart()

        if(isLogin()){
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            userIcon?.let { mUserIconIv.loadUrl(it) }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        }else{
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = context?.getText(R.string.un_login_text)
        }
    }
}