package com.yhy.order.presenter.view

import com.yhy.base.presenter.view.BaseView
import com.yhy.order.data.protocol.ShipAddress

interface AddressListView: BaseView {

    fun onGetAddressResult(result: MutableList<ShipAddress>?)

    fun onEditAddressResult(result: Boolean)

    fun onDeleteAddressResult(result: Boolean)

}