package com.yhy.order.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.yhy.base.ext.onClick
import com.yhy.base.ext.startLoading
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.order.R
import com.yhy.order.common.OrderConstant
import com.yhy.order.common.OrderConstant.Companion.KEY_FROM_ME
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.event.EditAddressEvent
import com.yhy.order.event.SelectAddressEvent
import com.yhy.order.injection.component.DaggerAddressComponent
import com.yhy.order.injection.module.AddressModule
import com.yhy.order.presenter.AddressPresenter
import com.yhy.order.presenter.view.AddressListView
import com.yhy.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*

class AddressListActivity: BaseMvpActivity<AddressPresenter>(), AddressListView {

    lateinit var  mAdapter: ShipAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        initView()
        initData()
        initListener()
    }

    private fun initView() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        mAddressRv.layoutManager = manager
        mAdapter = ShipAddressAdapter(this)
        mAdapter.mListener = object: ShipAddressAdapter.OptClickListener{
            override fun onSetDefaultCallBack(address: ShipAddress) {
                if(address.shipIsDefault == 0){
                    Toast.makeText(this@AddressListActivity, "该地址已是默认地址", Toast.LENGTH_SHORT).show()
                    return
                }
                mPresenter.editShipAddress(address.id, address.shipUserName, address.shipUserMobile, address.shipAddress, 0)
            }

            override fun onEditCallBack(address: ShipAddress) {
                val intent = Intent(this@AddressListActivity, AddressEditActivity::class.java)
                intent.putExtra(OrderConstant.KEY_SHIP_ADDRESS, address)
                startActivity(intent)
            }

            override fun onDeleteCallBack(address: ShipAddress) {
                mPresenter.deleteShipAddress(address.id)
            }
        }

        mAddressRv.adapter = mAdapter

        if(intent.getBooleanExtra(KEY_FROM_ME, false).not()){
            mAdapter.mItemClickListener = object: BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
                override fun onItemClick(item: ShipAddress, position: Int) {
                    Bus.send(SelectAddressEvent(item))
                    finish()
                }
            }
        }
    }

    private fun initData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    private fun initListener() {

        //添加或修改地址的回调
        Bus.observe<EditAddressEvent>()
            .subscribe {
                initData()
            }
            .registerInBus(this)

        mAddAddressBtn.onClick {
            startActivity(Intent(this, AddressEditActivity::class.java))
        }
    }

    override fun injectComponent() {
        DaggerAddressComponent.builder()
            .activityComponent(activityComponent)
            .addressModule(AddressModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetAddressResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0){
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onEditAddressResult(result: Boolean) {
        Toast.makeText(this, "地址修改成功",Toast.LENGTH_SHORT).show()
        initData()
    }

    override fun onDeleteAddressResult(result: Boolean) {
        Toast.makeText(this, "删除地址成功",Toast.LENGTH_SHORT).show()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}