package com.yhy.pay.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.utils.YuanFenConverter
import com.yhy.pay.R
import com.yhy.pay.injection.component.DaggerPayComponent
import com.yhy.pay.injection.module.PayModule
import com.yhy.pay.presenter.PayPresenter
import com.yhy.pay.presenter.view.PayView
import kotlinx.android.synthetic.main.activity_cash_register.*

//@Route(path = RouterPath.PayCenter.PATH_CASH_REGISTER)
class CashRegisterActivity: BaseMvpActivity<PayPresenter>(), PayView {

    //ARouter方式获取Intent数据 步骤一 加注解(Kotlin代码必须加@JvmField, java代码不用)
    //ARouter方式获取Intent数据 步骤二 注册 放BaseActivity里面
//    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
//    @JvmField
    var mOrderId = 0

//    @Autowired(name = ProviderConstant.KEY_ORDER_PRICE)
//    @JvmField
    var mOrderPrice: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        //设置支付宝沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        upDatePayType(true, false, false)

        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mOrderPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)

        mTotalPriceTv.text = "需支付 ¥${ YuanFenConverter.changeF2Y(mOrderPrice)}"
        mPayBtn.onClick {
            mPresenter.getPaySign(mOrderId, mOrderPrice)
        }
        mAlipayTypeTv.onClick { upDatePayType(true, false, false) }
        mWeixinTypeTv.onClick { upDatePayType(false, true, false)}
        mBankCardTypeTv.onClick { upDatePayType(false, false, true) }
    }

    private fun upDatePayType(isAli: Boolean, isWeiXin: Boolean, isBankCard: Boolean){
        mAlipayTypeTv.isSelected = isAli
        mWeixinTypeTv.isSelected = isWeiXin
        mBankCardTypeTv.isSelected = isBankCard
    }

    override fun injectComponent() {
        DaggerPayComponent.builder()
            .activityComponent(activityComponent)
            .payModule(PayModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetPaySignResult(result: String) {
//        Toast.makeText(this, "result = $result", Toast.LENGTH_SHORT).show()

        mPresenter.payOrder(mOrderId)
        //没有沙箱环境.........
//        Thread {
//            val resultMap:Map<String,String> = PayTask(this@CashRegisterActivity).payV2(result,true)
//             runOnUiThread{
//                 if (resultMap["resultStatus"].equals("9000")){
//                     mPresenter.payOrder(mOrderId)
//                 }else{
//                     Toast.makeText(this, "支付失败${resultMap["memo"]}", Toast.LENGTH_SHORT).show()
//                 }
//             }
//        }.run()

    }

    override fun onPayOrderResult(result: Boolean) {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show()
        finish()
    }


}