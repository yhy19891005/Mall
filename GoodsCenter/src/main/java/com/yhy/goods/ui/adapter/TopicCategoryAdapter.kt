package com.yhy.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.goods.R
import com.yhy.goods.data.protocol.Category
import kotlinx.android.synthetic.main.layout_top_category_item.view.*

/*
    二级商品分类Adapter
 */
class TopicCategoryAdapter(context: Context): BaseRecyclerViewAdapter<Category, TopicCategoryAdapter.TopicCategoryHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicCategoryHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_top_category_item,parent,false)
        return TopicCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: TopicCategoryHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //分类名称
        holder.itemView.mTopCategoryNameTv.text = model.categoryName
        //是否被选中
        holder.itemView.mTopCategoryNameTv.isSelected = model.isSelected
    }
    class TopicCategoryHolder(view: View): RecyclerView.ViewHolder(view)
}