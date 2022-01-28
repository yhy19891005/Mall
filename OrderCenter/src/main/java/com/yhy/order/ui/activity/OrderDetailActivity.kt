package com.yhy.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.utils.YuanFenConverter
import com.yhy.order.R
import com.yhy.order.data.protocol.Order
import com.yhy.order.injection.component.DaggerOrderComponent
import com.yhy.order.injection.module.OrderModule
import com.yhy.order.presenter.OrderDetailPresenter
import com.yhy.order.presenter.view.OrderDetailView
import com.yhy.order.ui.adapter.OrderGoodsAdapter
import kotlinx.android.synthetic.main.activity_order_detail.*

@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
class OrderDetailActivity: BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    lateinit var mAdapter: OrderGoodsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initView()
        initData()
    }

    private fun initView() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        mOrderGoodsRv.layoutManager = manager

        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun initData() {
        val orderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mPresenter.getOrderById(orderId)
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetOrderDetailResult(result: Order) {
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText("¥ ${YuanFenConverter.changeF2Y(result.totalPrice)}")

        mAdapter.setData(result.orderGoodsList)
    }


}