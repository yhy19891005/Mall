package com.yhy.order.service

import com.yhy.order.data.protocol.ShipAddress
import rx.Observable

interface AddressService {

    /**
     * 添加收货地址
     * */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<Boolean>

    /**
     * 删除收货地址
     * */
    fun deleteShipAddress(id: Int): Observable<Boolean>

    /**
     * 修改收货地址
     * */
    fun editShipAddress(id:Int, shipUserName:String, shipUserMobile:String, shipAddress:String, shipIsDefault:Int): Observable<Boolean>

    /**
     * 获取收货地址列表
     * */
    fun getShipAddressList(): Observable<MutableList<ShipAddress>?>
}