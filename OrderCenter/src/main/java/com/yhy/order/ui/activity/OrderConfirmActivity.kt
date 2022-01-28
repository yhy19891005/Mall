package com.yhy.order.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.ext.onClick
import com.yhy.base.ext.setVisible
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.utils.YuanFenConverter
import com.yhy.order.R
import com.yhy.order.data.protocol.Order
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.event.SelectAddressEvent
import com.yhy.order.injection.component.DaggerOrderComponent
import com.yhy.order.injection.module.OrderModule
import com.yhy.order.presenter.OrderPresenter
import com.yhy.order.presenter.view.OrderView
import com.yhy.order.ui.adapter.OrderGoodsAdapter
import com.yhy.pay.ui.activity.CashRegisterActivity
import kotlinx.android.synthetic.main.activity_order_confirm.*

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity: BaseMvpActivity<OrderPresenter>(), OrderView {

    lateinit var mAdapter: OrderGoodsAdapter
    private var mCurrentOrder: Order? = null
    private var mAddress: ShipAddress? = null

    //ARouter方式获取Intent数据 步骤一 加注解(Kotlin代码必须加@JvmField, java代码不用)
    //ARouter方式获取Intent数据 步骤二 注册 放BaseActivity里面
    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var orderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        initData()
        initListener()
    }

    private fun initView() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        mOrderGoodsRv.layoutManager = manager

        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun initData() {
        //传统方式获取Intent数据
        //val orderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mPresenter.getOrderById(orderId)
    }

    private fun initListener() {
        //选择收货地址
        mSelectShipTv.onClick {
            startActivity(Intent(this,AddressListActivity::class.java))
        }

        //选择收货地址
        mShipView.onClick {
            startActivity(Intent(this,AddressListActivity::class.java))
        }

        //选择地址的回调
        Bus.observe<SelectAddressEvent>()
            .subscribe {
                upDateAddressView(it.address)
            }
            .registerInBus(this)

        //提交订单
        mSubmitOrderBtn.onClick {
            if(mAddress == null){
                Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            mCurrentOrder?.let { mPresenter.submitOrder(it) }
        }
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetOrderGoodsResult(result: Order) {
        mCurrentOrder = result
        mTotalPriceTv.text = "合计: ¥ ${YuanFenConverter.changeF2Y(result.totalPrice)}"
        mAdapter.setData(result.orderGoodsList)
        mAddress = result.shipAddress
        upDateAddressView(result.shipAddress)
    }

    override fun onSubmitOrderResult(result: Boolean) {
        Toast.makeText(this, "订单提交成功", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, CashRegisterActivity::class.java);
        intent.putExtra(ProviderConstant.KEY_ORDER_ID, mCurrentOrder?.id?: 0)
        intent.putExtra(ProviderConstant.KEY_ORDER_PRICE, mCurrentOrder?.totalPrice?: 0)
        startActivity(intent)
        finish()
    }

    private fun upDateAddressView(address: ShipAddress?){
        if(address == null){
            mSelectShipTv.setVisible(true)
            mShipView.setVisible(false)
            mShipNameTv.text = ""
            mShipAddressTv.text = ""
        }else{
            mSelectShipTv.setVisible(false)
            mShipView.setVisible(true)
            mShipNameTv.text = "${address!!.shipUserName}   ${address!!.shipUserMobile}"
            mShipAddressTv.text = address!!.shipAddress
            mCurrentOrder?.shipAddress = address

            mAddress = address
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}