package com.yhy.base.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yhy.base.R
import com.yhy.base.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*

class HeaderBar @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attr, defStyleAttr) {

    private var isShowBack: Boolean = true
    private var rightText: String? = null
    private var titleText: String? = null

        init {
           val attributes = context.obtainStyledAttributes(attr, R.styleable.HeaderBar)
            isShowBack = attributes.getBoolean(R.styleable.HeaderBar_isShowBack,true)
            rightText = attributes.getString(R.styleable.HeaderBar_rightText)
            titleText = attributes.getString(R.styleable.HeaderBar_titleText)
            attributes.recycle()

            initView()
        }

    private fun initView() {
        View.inflate(context,R.layout.layout_header_bar,this)
        mLeftIv.visibility = if(isShowBack) View.VISIBLE else View.GONE
        rightText?.let {
            mRightTv.visibility = View.VISIBLE
            mRightTv.text = it
        }
        titleText?.let {
            mTitleTv.text = it
        }

        mLeftIv.onClick {
            if(context is Activity){
                (context as Activity).finish()
            }
        }
    }

    fun getLeftView(): View = mLeftIv

    fun getRightView(): View = mRightTv

    fun getRightText(): String = mRightTv.text.toString()

    fun setRightText(str: String){
        mRightTv.text = str
    }
}