package com.yhy.message.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.ext.loadUrl
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.message.R
import com.yhy.message.data.protocol.Message
import kotlinx.android.synthetic.main.layout_message_item.view.*

class MsgAdapter(context: Context): BaseRecyclerViewAdapter<Message, MsgAdapter.MsgHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_message_item,parent,false)
        return MsgHolder(view)
    }

    override fun onBindViewHolder(holder: MsgHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        holder.itemView.mMsgIconIv.loadUrl(item.msgIcon)
        holder.itemView.mMsgTitleTv.text = item.msgTitle
        holder.itemView.mMsgContentTv.text = item.msgContent
        holder.itemView.mMsgTimeTv.text = item.msgTime
    }

    class MsgHolder(view: View): RecyclerView.ViewHolder(view)
}