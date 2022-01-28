package com.yhy.goods.widget

import android.text.Editable

import android.text.method.DigitsKeyListener

import android.content.Context

import android.content.res.TypedArray

import android.widget.EditText

import android.widget.TextView

import android.view.LayoutInflater

import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View

import android.widget.LinearLayout
import com.yhy.goods.R
import java.lang.NumberFormatException

/**
 * 购物车商品数量、增加和减少控制按钮。
 */
class NumberButton(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs), View.OnClickListener, TextWatcher {
    //库存
    var inventory = Int.MAX_VALUE
        private set

    //最大购买数，默认无限制
    var buyMax = Int.MAX_VALUE
        private set
    private var mCount: EditText? = null
    private var mOnWarnListener: OnWarnListener? = null

    constructor(context: Context) : this(context, null) {}

    //
    //    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
    //        super(context, attrs, defStyleAttr);
    //    }
    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.layout_number_btn, this)
        val addButton = findViewById<View>(R.id.button_add) as TextView
        addButton.setOnClickListener(this)
        val subButton = findViewById<View>(R.id.button_sub) as TextView
        subButton.setOnClickListener(this)
        mCount = findViewById<View>(R.id.text_count) as EditText
        mCount!!.addTextChangedListener(this)
        mCount!!.setOnClickListener(this)
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton)
        val editable = typedArray.getBoolean(R.styleable.NumberButton_editable, true)
        val buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1)
        val textWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textWidth, -1)
        val textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1)
        val textColor = typedArray.getColor(R.styleable.NumberButton_textColor, -0x1000000)
        typedArray.recycle()
        setEditable(editable)
        mCount!!.setTextColor(textColor)
        subButton.setTextColor(textColor)
        addButton.setTextColor(textColor)
        if (textSize > 0) mCount!!.textSize = textSize.toFloat()
        if (buttonWidth > 0) {
            val textParams = LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT)
            subButton.layoutParams = textParams
            addButton.layoutParams = textParams
        }
        if (textWidth > 0) {
            val textParams = LayoutParams(textWidth, LayoutParams.MATCH_PARENT)
            mCount!!.layoutParams = textParams
        }
    }

    val number: Int
        get() {
            try {
                return mCount!!.text.toString().toInt()
            } catch (e: NumberFormatException) {
            }
            mCount!!.setText("1")
            return 1
        }

    override fun onClick(v: View) {
        val id: Int = v.getId()
        val count = number
        if (id == R.id.button_sub) {
            if (count > 1) {
                //正常减
                mCount!!.setText("" + (count - 1))
            }
        } else if (id == R.id.button_add) {
            if (count < Math.min(buyMax, inventory)) {
                //正常添加
                mCount!!.setText("" + (count + 1))
            } else if (inventory < buyMax) {
                //库存不足
                warningForInventory()
            } else {
                //超过最大购买数
                warningForBuyMax()
            }
        } else if (id == R.id.text_count) {
            mCount!!.setSelection(mCount!!.text.toString().length)
        }
    }

    private fun onNumberInput() {
        //当前数量
        val count = number
        if (count <= 0) {
            //手动输入
            mCount!!.setText("1")
            return
        }
        val limit = Math.min(buyMax, inventory)
        if (count > limit) {
            //超过了数量
            mCount!!.setText(limit.toString() + "")
            if (inventory < buyMax) {
                //库存不足
                warningForInventory()
            } else {
                //超过最大购买数
                warningForBuyMax()
            }
        }
    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private fun warningForInventory() {
        if (mOnWarnListener != null) mOnWarnListener!!.onWarningForInventory(inventory)
    }

    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private fun warningForBuyMax() {
        if (mOnWarnListener != null) mOnWarnListener!!.onWarningForBuyMax(buyMax)
    }

    private fun setEditable(editable: Boolean) {
        if (editable) {
            mCount!!.isFocusable = true
            mCount!!.keyListener = DigitsKeyListener()
        } else {
            mCount!!.isFocusable = false
            mCount!!.keyListener = null
        }
    }

    fun setCurrentNumber(currentNumber: Int): NumberButton {
        if (currentNumber < 1) mCount!!.setText("1")
        mCount!!.setText("" + Math.min(Math.min(buyMax, inventory), currentNumber))
        return this
    }

    fun setInventory(inventory: Int): NumberButton {
        this.inventory = inventory
        return this
    }

    fun setBuyMax(buyMax: Int): NumberButton {
        this.buyMax = buyMax
        return this
    }

    fun setOnWarnListener(onWarnListener: OnWarnListener?): NumberButton {
        mOnWarnListener = onWarnListener
        return this
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onNumberInput()
    }

    override fun afterTextChanged(s: Editable) {}
    interface OnWarnListener {
        fun onWarningForInventory(inventory: Int)
        fun onWarningForBuyMax(max: Int)
    }

    init {
        init(context, attrs)
    }
}