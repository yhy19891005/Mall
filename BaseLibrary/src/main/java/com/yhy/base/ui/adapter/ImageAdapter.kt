package com.yhy.base.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yhy.base.data.protocol.BannerInfo
import com.yhy.base.utils.GlideUtils
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(context: Context, datas: List<BannerInfo>): BannerAdapter<BannerInfo, ImageAdapter.BannerViewHolder>(datas) {

    private val mContext = context

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.context)
        //注意,必须设置为match_parent,这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, info: BannerInfo?, position: Int, size: Int) {
        if(holder != null && info != null){
            GlideUtils.loadImage(mContext, info.imgUrl, holder.itemView as ImageView)
        }
    }

    class BannerViewHolder(iv: ImageView): RecyclerView.ViewHolder(iv)
}