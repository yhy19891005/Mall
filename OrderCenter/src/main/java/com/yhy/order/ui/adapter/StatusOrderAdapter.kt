package com.yhy.order.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.loadUrl
import com.yhy.base.ext.onClick
import com.yhy.base.ext.setVisible
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.utils.YuanFenConverter
import com.yhy.base.widget.badge.DisplayUtil
import com.yhy.order.R
import com.yhy.order.common.OrderConstant
import com.yhy.order.common.OrderStatus
import com.yhy.order.data.protocol.Order
import kotlinx.android.synthetic.main.layout_order_item.view.*

class StatusOrderAdapter(context: Context): BaseRecyclerViewAdapter<Order, StatusOrderAdapter.StatusOrderHolder>(context) {

    var mListener: OnOptClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusOrderHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_order_item,parent,false)
        return StatusOrderHolder(view)
    }

    override fun onBindViewHolder(holder: StatusOrderHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        when(item.orderStatus){
            OrderStatus.ORDER_WAIT_PAY ->{
                holder.itemView.mOrderStatusNameTv.text = "待支付"
                holder.itemView.mBottomView.setVisible(true)
                holder.itemView.mConfirmBtn.setVisible(false)
                holder.itemView.mPayBtn.setVisible(true)
                holder.itemView.mCancelBtn.setVisible(true)
            }
            OrderStatus.ORDER_WAIT_CONFIRM ->{
                holder.itemView.mOrderStatusNameTv.text = "待收货"
                holder.itemView.mBottomView.setVisible(true)
                holder.itemView.mConfirmBtn.setVisible(true)
                holder.itemView.mPayBtn.setVisible(false)
                holder.itemView.mCancelBtn.setVisible(false)
            }
            OrderStatus.ORDER_COMPLETED ->{
                holder.itemView.mOrderStatusNameTv.text = "已完成"
                holder.itemView.mBottomView.setVisible(false)
                holder.itemView.mConfirmBtn.setVisible(false)
                holder.itemView.mPayBtn.setVisible(false)
                holder.itemView.mCancelBtn.setVisible(false)
            }
            OrderStatus.ORDER_CANCELED ->{
                holder.itemView.mOrderStatusNameTv.text = "已取消"
                holder.itemView.mBottomView.setVisible(false)
                holder.itemView.mConfirmBtn.setVisible(false)
                holder.itemView.mPayBtn.setVisible(false)
                holder.itemView.mCancelBtn.setVisible(false)
            }
            else -> {
                holder.itemView.mOrderStatusNameTv.text = "未知状态"
                holder.itemView.mBottomView.setVisible(false)
                holder.itemView.mConfirmBtn.setVisible(false)
                holder.itemView.mPayBtn.setVisible(false)
                holder.itemView.mCancelBtn.setVisible(false)
            }
        }
        var totalCount = 0
        if(item.orderGoodsList.size == 1){
            holder.itemView.mMultiGoodsView.setVisible(false)
            holder.itemView.mSingleGoodsView.setVisible(true)
            holder.itemView.mGoodsIconIv.loadUrl(item.orderGoodsList[0].goodsIcon)
            holder.itemView.mGoodsDescTv.text = item.orderGoodsList[0].goodsDesc
            holder.itemView.mGoodsPriceTv.text = "¥${YuanFenConverter.changeF2Y(item.totalPrice)}"
            holder.itemView.mGoodsCountTv.text = "X ${item.orderGoodsList[0].goodsCount}"
            totalCount = item.orderGoodsList[0].goodsCount
        }else{
            holder.itemView.mMultiGoodsView.setVisible(true)
            holder.itemView.mSingleGoodsView.setVisible(false)
            //先移除mMultiGoodsView的所有子视图
            holder.itemView.mMultiGoodsView.removeAllViews()
//            val manager = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
            val manager = ViewGroup.MarginLayoutParams(DisplayUtil.dp2px(mContext, 60F), DisplayUtil.dp2px(mContext, 60F))
            manager.setMargins(16,8,16,8)
            for (goods in item.orderGoodsList){
                val img = ImageView(mContext)
                img.scaleType = ImageView.ScaleType.CENTER_CROP
                img.loadUrl(goods.goodsIcon)
                holder.itemView.mMultiGoodsView.addView(img, manager)
                totalCount += goods.goodsCount
            }
        }
        holder.itemView.mOrderInfoTv.text = "共计 $totalCount 件商品, 总价 ¥${YuanFenConverter.changeF2Y(item.totalPrice)}"

        //确认订单
        holder.itemView.mConfirmBtn.onClick { mListener?.onOptClick(OrderConstant.OPT_ORDER_CONFIRM, item) }
        //去支付
        holder.itemView.mPayBtn.onClick {  mListener?.onOptClick(OrderConstant.OPT_ORDER_PAY, item) }
        //取消订单
        holder.itemView.mCancelBtn.onClick {  mListener?.onOptClick(OrderConstant.OPT_ORDER_CANCEL, item) }
    }

    class StatusOrderHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnOptClickListener{
        fun onOptClick(optType: Int, order: Order)
    }
}