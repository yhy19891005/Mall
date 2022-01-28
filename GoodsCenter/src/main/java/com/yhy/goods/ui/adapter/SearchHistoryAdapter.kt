package com.yhy.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.goods.R
import kotlinx.android.synthetic.main.layout_search_history_item.view.*


class SearchHistoryAdapter(context: Context): BaseRecyclerViewAdapter<String, SearchHistoryAdapter.SearchHistoryHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_search_history_item,parent,false)
        return SearchHistoryHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.mSearchHistoryTv.text = dataList[position]
    }
    class SearchHistoryHolder(view: View): RecyclerView.ViewHolder(view)
}