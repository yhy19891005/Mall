package com.yhy.order.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.presenter.view.AddressListView
import com.yhy.order.service.AddressService
import javax.inject.Inject

class AddressPresenter @Inject constructor(): BasePresenter<AddressListView>() {

    //dagger注入AddressService实例
    @Inject
    lateinit var service: AddressService

    /**
     * 获取收货地址列表
     * */
    fun getShipAddressList(){
        if(!checkNet()) return
        mView.showLoading()

        service.getShipAddressList()
            .excute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView){
                override fun onNext(t: MutableList<ShipAddress>?) {
                    mView.onGetAddressResult(t)
                }
            },provider)
    }

    /**
     * 修改收货地址
     * */
    fun editShipAddress(id:Int, shipUserName:String, shipUserMobile:String, shipAddress:String, shipIsDefault:Int){
        if(!checkNet()) return
        mView.showLoading()

        service.editShipAddress(id, shipUserName, shipUserMobile, shipAddress, shipIsDefault)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onEditAddressResult(t)
                }
            },provider)
    }

    /**
     * 删除收货地址
     * */
    fun deleteShipAddress(id: Int){
        if(!checkNet()) return
        mView.showLoading()

        service.deleteShipAddress(id)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onDeleteAddressResult(t)
                }
            },provider)
    }
}