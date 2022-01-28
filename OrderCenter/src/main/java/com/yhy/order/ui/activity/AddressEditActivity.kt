package com.yhy.order.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.yhy.base.ext.onClick
import com.yhy.base.rx.Bus
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.order.R
import com.yhy.order.common.OrderConstant
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.event.EditAddressEvent
import com.yhy.order.injection.component.DaggerAddressComponent
import com.yhy.order.injection.module.AddressModule
import com.yhy.order.presenter.AddressEditPresenter
import com.yhy.order.presenter.view.AddressEditView
import kotlinx.android.synthetic.main.activity_edit_address.*

class AddressEditActivity: BaseMvpActivity<AddressEditPresenter>(), AddressEditView {

    var address: ShipAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        initView()
        initListener()
    }

    private fun initView() {
        address = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        address?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }
    }

    private fun initListener() {
        mSaveBtn.onClick {
            if(!hasInputContent()){
                Toast.makeText(this, "请先输入相关内容",Toast.LENGTH_SHORT).show()
                return@onClick
            }
            val shipName = mShipNameEt.text.toString().trim()
            val shipMobile = mShipMobileEt.text.toString().trim()
            val shipAddress = mShipAddressEt.text.toString().trim()
            if(address == null){
                //address为null, 说明是新增地址
                mPresenter.addShipAddress(shipName,shipMobile,shipAddress)
            }else{
                //address不为null, 说明是修改地址
                mPresenter.editShipAddress(address!!.id, shipName, shipMobile, shipAddress, address!!.shipIsDefault)
            }
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

    override fun onEditAddressResult(result: Boolean) {
        if(address == null){
            Toast.makeText(this, "添加地址成功",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "地址修改成功",Toast.LENGTH_SHORT).show()
        }
        Bus.send(EditAddressEvent())
        finish()
    }

    private fun hasInputContent(): Boolean{
        return  mShipNameEt.text.toString().trim().isNullOrBlank().not() and
                mShipMobileEt.text.toString().trim().isNullOrBlank().not() and
                mShipAddressEt.text.toString().trim().isNullOrBlank().not()
    }
}