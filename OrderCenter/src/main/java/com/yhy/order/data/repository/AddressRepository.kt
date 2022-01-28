package com.yhy.order.data.repository

import com.yhy.base.data.net.RetrofitFactory
import com.yhy.base.data.protocol.BaseResp
import com.yhy.order.data.api.ShipAddressApi
import com.yhy.order.data.protocol.*
import rx.Observable
import javax.inject.Inject

class AddressRepository @Inject constructor() {

    /**
     * 添加收货地址
     * */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(ShipAddressApi::class.java)
            .addShipAddress(AddShipAddressReq(shipUserName, shipUserMobile, shipAddress))
    }

    /**
     * 删除收货地址
     * */
    fun deleteShipAddress(id: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE
            .create(ShipAddressApi::class.java)
            .deleteShipAddress(DeleteShipAddressReq(id))
    }

    /**
     * 修改收货地址
     * */
    fun editShipAddress(id:Int, shipUserName:String, shipUserMobile:String, shipAddress:String, shipIsDefault:Int): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE
            .create(ShipAddressApi::class.java)
            .editShipAddress(EditShipAddressReq(id, shipUserName, shipUserMobile, shipAddress, shipIsDefault))
    }

    /**
     * 查询收货地址列表
     * */
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>> {
        return RetrofitFactory.INSTANCE
            .create(ShipAddressApi::class.java)
            .getShipAddressList()
    }
}