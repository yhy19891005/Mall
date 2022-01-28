package com.yhy.order.event

import com.yhy.order.data.protocol.ShipAddress


/*
    选择收货人信息事件
 */
class SelectAddressEvent(val address: ShipAddress)
