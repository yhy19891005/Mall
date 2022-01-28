package com.yhy.base.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.yhy.base.R


/*
    获取验证码按钮
    带倒计时
 */
class VerifyButtonBak(mContext: Context, attrs: AttributeSet) : AppCompatButton(mContext, attrs) {
    private val mHandler: Handler
    private var mCount = 60
    private var mOnVerifyBtnClick: OnVerifyBtnClick? = null

    init {
        this.text = "获取验证码"
        mHandler = Handler()

    }

    /*
        倒计时，并处理点击事件
     */
    fun requestSendVerifyNumber() {
        mHandler.postDelayed(countDown, 0)
        if (mOnVerifyBtnClick != null) {
            mOnVerifyBtnClick!!.onClick()
        }

    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        override fun run() {
            this@VerifyButtonBak.text = mCount.toString() + "s "
            this@VerifyButtonBak.setBackgroundColor(ContextCompat.getColor(context,R.color.common_blue_dark))
            this@VerifyButtonBak.setTextColor(ContextCompat.getColor(context,R.color.common_white))
            this@VerifyButtonBak.isEnabled = false

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCount--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    /*
        恢复到初始状态
     */
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "重获验证码"
        }

        this.setBackgroundColor(ContextCompat.getColor(context,R.color.common_blue))
        this.setTextColor(ContextCompat.getColor(context,R.color.common_white))
        mCount = 60
    }

    /*
        点击事件接口
     */
    interface OnVerifyBtnClick {
        fun onClick()
    }

    fun setOnVerifyBtnClick(onVerifyBtnClick: OnVerifyBtnClick) {
        this.mOnVerifyBtnClick = onVerifyBtnClick
    }
}
