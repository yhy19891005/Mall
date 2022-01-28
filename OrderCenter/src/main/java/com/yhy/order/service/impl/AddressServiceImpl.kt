package com.yhy.order.service.impl

import com.yhy.base.ext.covert
import com.yhy.base.ext.covertBoolean
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.data.repository.AddressRepository
import com.yhy.order.service.AddressService
import rx.Observable
import javax.inject.Inject

class AddressServiceImpl @Inject constructor():  AddressService{

    //AddressRepository实例,dagger注入
    @Inject
    lateinit var repository: AddressRepository

    /**
     * 添加收货地址
     * */
    override fun addShipAddress(
        shipUserName: String,
        shipUserMobile: String,
        shipAddress: String
    ): Observable<Boolean>  = repository.addShipAddress(shipUserName, shipUserMobile, shipAddress).covertBoolean()

    /**
     * 删除收货地址
     * */
    override fun deleteShipAddress(id: Int): Observable<Boolean> = repository.deleteShipAddress(id).covertBoolean()

    /**
     * 修改收货地址
     * */
    override fun editShipAddress(
        id: Int,
        shipUserName: String,
        shipUserMobile: String,
        shipAddress: String,
        shipIsDefault: Int
    ): Observable<Boolean>  = repository.editShipAddress(id,shipUserName, shipUserMobile, shipAddress, shipIsDefault).covertBoolean()

    /**
     * 获取收货地址列表
     * */
    override fun getShipAddressList(): Observable<MutableList<ShipAddress>?> = repository.getShipAddressList().covert()
}