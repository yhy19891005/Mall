package com.yhy.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.loadUrl
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.utils.YuanFenConverter
import com.yhy.goods.R
import com.yhy.goods.data.protocol.Goods
import kotlinx.android.synthetic.main.layout_goods_item.view.*

/*
    商品列表页adapter
 */
class GoodsAdapter(context: Context): BaseRecyclerViewAdapter<Goods, GoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_goods_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //商品图片
        holder.itemView.mGoodsIconIv.loadUrl(model.goodsDefaultIcon)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        //商品价格
        holder.itemView.mGoodsPriceTv.text =  YuanFenConverter.changeF2Y(model.goodsDefaultPrice)
        //商品销量及库存
        holder.itemView.mGoodsSalesStockTv.text = "销量${model.goodsSalesCount}件     库存${model.goodsStockCount}"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

}
