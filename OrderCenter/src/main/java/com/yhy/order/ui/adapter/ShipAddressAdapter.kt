package com.yhy.order.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.onClick
import com.yhy.base.rx.Bus
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.order.R
import com.yhy.order.data.protocol.ShipAddress
import com.yhy.order.event.SelectAddressEvent
import kotlinx.android.synthetic.main.layout_address_item.view.*

class ShipAddressAdapter(context: Context): BaseRecyclerViewAdapter<ShipAddress, ShipAddressAdapter.ShipAddressHolder>(context) {

    var mListener: OptClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipAddressHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_address_item,parent,false)
        return ShipAddressHolder(view)
    }

    override fun onBindViewHolder(holder: ShipAddressHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        holder.itemView.mSetDefaultTv.isSelected = (item.shipIsDefault == 0)
        holder.itemView.mShipNameTv.text = "${item.shipUserName}    ${item.shipUserMobile}"
        holder.itemView.mShipAddressTv.text = item.shipAddress

        holder.itemView.mSetDefaultTv.onClick {
            mListener?.onSetDefaultCallBack(item)
        }
        holder.itemView.mEditTv.onClick {
            mListener?.onEditCallBack(item)
        }
        holder.itemView.mDeleteTv.onClick {
            mListener?.onDeleteCallBack(item)
        }
    }

    class ShipAddressHolder(view: View): RecyclerView.ViewHolder(view)

    interface OptClickListener{
        //设为默认地址
        fun onSetDefaultCallBack(address: ShipAddress)
        //编辑
        fun onEditCallBack(address: ShipAddress)
        //删除
        fun onDeleteCallBack(address: ShipAddress)
    }
}