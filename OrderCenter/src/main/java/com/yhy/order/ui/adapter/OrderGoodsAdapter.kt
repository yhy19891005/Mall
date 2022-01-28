package com.yhy.order.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.loadUrl
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.utils.YuanFenConverter
import com.yhy.order.R
import com.yhy.order.data.protocol.OrderGoods
import kotlinx.android.synthetic.main.layout_order_goods_item.view.*

class OrderGoodsAdapter(context: Context): BaseRecyclerViewAdapter<OrderGoods, OrderGoodsAdapter.OrderGoodsHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderGoodsHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_order_goods_item,parent,false)
        return OrderGoodsHolder(view)
    }

    override fun onBindViewHolder(holder: OrderGoodsHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        holder.itemView.mGoodsIconIv.loadUrl(item.goodsIcon)
        holder.itemView.mGoodsDescTv.text = item.goodsDesc
        holder.itemView.mGoodsSkuTv.text = item.goodsSku
        holder.itemView.mGoodsPriceTv.text =  "¥ ${YuanFenConverter.changeF2Y(item.goodsPrice)}"
        holder.itemView.mGoodsCountTv.text = "${item.goodsCount}件"
    }

    class OrderGoodsHolder(view: View): RecyclerView.ViewHolder(view)
}