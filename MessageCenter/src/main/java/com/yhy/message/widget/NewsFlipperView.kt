package com.yhy.message.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ViewFlipper
import android.widget.TextView
import com.yhy.message.R


/*
    公告组件封装
 */
class NewsFlipperView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val mFlipperView: ViewFlipper
    private val mContext = context

    init {
        val rootView = View.inflate(context, R.layout.layout_news_flipper, null)
        mFlipperView = rootView.findViewById(R.id.mFlipperView)
        mFlipperView.setInAnimation(context, R.anim.news_bottom_in)
        mFlipperView.setOutAnimation(context, R.anim.news_bottom_out)

        addView(rootView)
    }


    /*
        构建公告
     */
    private fun buildNewsView(text: String): View {
        val textView = TextView(context)
        textView.text = text
        textView.textSize = px2sp(mContext.resources.getDimension(R.dimen.text_small_size))
        textView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return textView
    }

    private fun px2sp(dimension: Float): Float {
        val fontScale = mContext.resources.displayMetrics.scaledDensity
        return dimension / fontScale + 0.5f
    }

    /*
        设置公告数据
     */
    fun setData(data: Array<String>) {
        for (text in data) {
            mFlipperView.addView(buildNewsView(text))
        }
        mFlipperView.startFlipping()
    }
}
