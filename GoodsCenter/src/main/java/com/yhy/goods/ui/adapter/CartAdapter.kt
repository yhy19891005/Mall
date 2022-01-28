package com.yhy.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.loadUrl
import com.yhy.base.ext.onClick
import com.yhy.base.rx.Bus
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.ui.adapter.DefaultTextWatcher
import com.yhy.base.utils.YuanFenConverter
import com.yhy.goods.R
import com.yhy.goods.common.getEditText
import com.yhy.goods.data.protocol.CartGoods
import com.yhy.goods.event.CartAllCheckedEvent
import com.yhy.goods.event.UpdateTotalPriceEvent
import kotlinx.android.synthetic.main.layout_cart_goods_item.view.*

class CartAdapter(context: Context): BaseRecyclerViewAdapter<CartGoods, CartAdapter.CartHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_cart_goods_item,parent,false)
        return CartHolder(view)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //是否选中
        holder.itemView.mCheckedCb.isChecked = model.isSelected
        //加载商品图片
        holder.itemView.mGoodsIconIv.loadUrl(model.goodsIcon)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        //商品SKU
        holder.itemView.mGoodsSkuTv.text = model.goodsSku
        //商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.goodsPrice)
        //商品数量
        holder.itemView.mGoodsCountBtn.setCurrentNumber(model.goodsCount)

        //选中按钮点击事件
        holder.itemView.mCheckedCb.onClick {
            model.isSelected = holder.itemView.mCheckedCb.isChecked
            val isAllSelected = dataList.all { it.isSelected }
            Bus.send(CartAllCheckedEvent(isAllSelected))
            Bus.send(UpdateTotalPriceEvent())
            notifyDataSetChanged()
        }

        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object: DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.goodsCount = s.toString().toInt()
                Bus.send(UpdateTotalPriceEvent())
            }
        })
    }

    class CartHolder(view: View): RecyclerView.ViewHolder(view)
}