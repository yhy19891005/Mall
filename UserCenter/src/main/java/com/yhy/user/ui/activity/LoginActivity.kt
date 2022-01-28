package com.yhy.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.yhy.base.ext.isEnable
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.common.RouterPath
import com.yhy.base.rx.Bus
import com.yhy.provider.event.LoginSucEvent
import com.yhy.provider.router.PushProvider
import com.yhy.user.R
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.injection.component.DaggerUserComponent
import com.yhy.user.injection.module.UserModule
import com.yhy.user.presenter.LoginPresenter
import com.yhy.user.presenter.view.LoginView
import com.yhy.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {

    //ARouter跨模块接口调用
    //步骤三: 在需要用到的地方声明自定义接口的变量, 并加上@Autowired注解
    //Kotlin代码需加上@JvmField注解, java代码不用
    //步骤二见PushProviderImpl
    //还有第四步骤: 注入,放在BaseActivity里面
    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginBtn.isEnable(mMobileEt) { hasInputContent() }
        mLoginBtn.isEnable(mPwdEt) { hasInputContent() }
        mLoginBtn.onClick{
            val phoneNum = mMobileEt.text.toString().trim()
            val psw = mPwdEt.text.toString().trim()

            mPresenter.login(phoneNum, psw, mPushProvider?.getPushId()?:"")
        }
        mHeaderBar.getRightView().onClick {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        mForgetPwdTv.onClick {
            startActivity(Intent(this@LoginActivity, ForgetPwdActivity::class.java))
        }
    }

    override fun onLoginResult(result: UserInfo) {
        Toast.makeText(this, "登录成功",Toast.LENGTH_SHORT).show()
        //保存用户信息
        UserPrefsUtils.putUserInfo(result)
        Bus.send(LoginSucEvent())
        finish()
//        startActivity(Intent(this@LoginActivity, UserInfoActivity::class.java))
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    private fun hasInputContent(): Boolean{
        return  mMobileEt.text.toString().trim().isNullOrBlank().not() and
                mPwdEt.text.toString().trim().isNullOrBlank().not()
    }
}