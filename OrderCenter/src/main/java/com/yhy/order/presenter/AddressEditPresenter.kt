package com.yhy.order.presenter

import com.yhy.base.ext.excute
import com.yhy.base.presenter.BasePresenter
import com.yhy.base.rx.BaseSubscriber
import com.yhy.order.presenter.view.AddressEditView
import com.yhy.order.service.AddressService
import javax.inject.Inject

class AddressEditPresenter @Inject constructor(): BasePresenter<AddressEditView>() {

    //dagger注入AddressService实例
    @Inject
    lateinit var service: AddressService

    /**
     *  添加收货地址
     * */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String){

        if(!checkNet()) return
        mView.showLoading()

        service.addShipAddress(shipUserName, shipUserMobile, shipAddress)
            .excute(object : BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.onEditAddressResult(t)
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
}