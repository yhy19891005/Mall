package com.yhy.order.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.ui.fragment.BaseMvpFragment
import com.yhy.order.R
import com.yhy.order.common.OrderConstant
import com.yhy.order.data.protocol.Order
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.injection.component.DaggerOrderComponent
import com.yhy.order.injection.module.OrderModule
import com.yhy.order.presenter.StatusOrderPresenter
import com.yhy.order.presenter.view.StatusOrderView
import com.yhy.order.ui.activity.OrderDetailActivity
import com.yhy.order.ui.adapter.StatusOrderAdapter
import com.yhy.pay.ui.activity.CashRegisterActivity
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment: BaseMvpFragment<StatusOrderPresenter>(), StatusOrderView{

    private lateinit var mAdapter: StatusOrderAdapter
    private var mOrderStatus = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mOrderStatus = it.getInt(OrderConstant.KEY_ORDER_STATUS)
        }
        initView()

        initListener()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initView() {
        activity?.let {
            val manager = LinearLayoutManager(it)
            manager.orientation = LinearLayoutManager.VERTICAL
            mOrderRv.layoutManager = manager
            mAdapter = StatusOrderAdapter(it)
            mOrderRv.adapter = mAdapter
        }
    }

    private fun initData() {
          mPresenter.getOrderList(mOrderStatus)
    }

    private fun initListener() {
        mAdapter.mListener = object: StatusOrderAdapter.OnOptClickListener{
            override fun onOptClick(optType: Int, order: Order) {
                when(optType){
                    //确认订单
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mPresenter.confirmOrder(order.id)
                    }
                    //去支付
                    OrderConstant.OPT_ORDER_PAY -> {
//                        ARouter.getInstance()
//                               .build(RouterPath.PayCenter.PATH_CASH_REGISTER)
//                               .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
//                               .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
//                               .navigation()
                        activity?.let {
                            val intent = Intent(it, CashRegisterActivity::class.java);
                            intent.putExtra(ProviderConstant.KEY_ORDER_ID, order.id)
                            intent.putExtra(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                            startActivity(intent)
                        }
                    }
                    //取消订单
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        mPresenter.cancelOrder(order.id)
                    }
                    else ->{
                        activity?.let {
                            Toast.makeText(it, "未知操作", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        mAdapter.mItemClickListener = object: BaseRecyclerViewAdapter.OnItemClickListener<Order>{
            override fun onItemClick(item: Order, position: Int) {
               activity?.let {
                   val intent = Intent(it, OrderDetailActivity::class.java)
                   intent.putExtra(ProviderConstant.KEY_ORDER_ID, item.id)
                   startActivity(intent)
               }
            }
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

    override fun onGetStatusOrderResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0){
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onConfirmOrderResult(result: Boolean) {
        activity?.let {
            Toast.makeText(it, "订单确认成功", Toast.LENGTH_SHORT).show()
        }
        initData()
    }

    override fun onCancelOrderResult(result: Boolean) {
        activity?.let {
            Toast.makeText(it, "订单取消成功", Toast.LENGTH_SHORT).show()
        }
        initData()
    }
}