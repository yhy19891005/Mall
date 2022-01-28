package com.yhy.mall.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.utils.GlideUtils
import com.yhy.mall.R
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*

class DiscountAdapter(context: Context): BaseRecyclerViewAdapter<String, DiscountAdapter.DiscountHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountHolder {
      val view = LayoutInflater.from(mContext)
           .inflate(R.layout.layout_home_discount_item,parent,false)
        return DiscountHolder(view)
    }

    override fun onBindViewHolder(holder: DiscountHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        GlideUtils.loadImage(mContext, dataList[position], holder.itemView.mGoodsIconIv)
        holder.itemView.mDiscountAfterTv.text = "￥ 1230"
        holder.itemView.mDiscountBeforeTv.text = "$ 1860"
    }

    class DiscountHolder(view: View): RecyclerView.ViewHolder(view)


}