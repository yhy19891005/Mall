package com.yhy.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.kotlin.provider.common.ProviderConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.ext.onClick
import com.yhy.base.ext.setVisible
import com.yhy.base.ext.startLoading
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.fragment.BaseMvpFragment
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.base.utils.YuanFenConverter
import com.yhy.goods.R
import com.yhy.goods.common.SP_CART_SIZE
import com.yhy.goods.data.protocol.CartGoods
import com.yhy.goods.event.CartAllCheckedEvent
import com.yhy.goods.event.UpdateCartSizeEvent
import com.yhy.goods.event.UpdateTotalPriceEvent
import com.yhy.goods.injection.component.DaggerCartComponent
import com.yhy.goods.injection.module.CartModule
import com.yhy.goods.presenter.CartPresenter
import com.yhy.goods.presenter.view.CartView
import com.yhy.goods.ui.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.mMultiStateView

class CartFragment: BaseMvpFragment<CartPresenter>(), CartView {

    private lateinit var mAdapter: CartAdapter

    private var mTotalPrice: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        upDateTotalPrice()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initView() {
        activity?.let {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            mCartGoodsRv.layoutManager = manager
            mAdapter = CartAdapter(it)

            mCartGoodsRv.adapter = mAdapter
        }
    }

    private fun initData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    private fun initListener(){
        //??????
        mAllCheckedCb.onClick {
            mAdapter.dataList.forEach {
                it.isSelected = mAllCheckedCb.isChecked
            }
            mAdapter.notifyDataSetChanged()
            upDateTotalPrice()
        }

        //????????????---????????????
        Bus.observe<CartAllCheckedEvent>()
            .subscribe {

                run {
                    mAllCheckedCb.isChecked = it.isAllChecked
                }
            }
            .registerInBus(this)

        //????????????---????????????
        Bus.observe<UpdateTotalPriceEvent>()
            .subscribe {

                run {
                    upDateTotalPrice()
                }
            }
            .registerInBus(this)

        //??????
        mHeaderBar.getRightView().onClick {
            val isEditStatus = mHeaderBar.getRightText() == resources.getString(R.string.common_edit)
            mTotalPriceTv.setVisible(isEditStatus.not())
            mSettleAccountsBtn.setVisible(isEditStatus.not())
            mDeleteBtn.setVisible(isEditStatus)
            if(isEditStatus){
                mHeaderBar.setRightText(resources.getString(R.string.common_complete))
            }else{
                mHeaderBar.setRightText(resources.getString(R.string.common_edit))
            }
        }

        //??????
        mDeleteBtn.onClick {
            val list: MutableList<Int> = arrayListOf()
            mAdapter.dataList
                .filter { it.isSelected }  //??????????????????
                .mapTo(list) { it.id } //???id??????list
            if(list.size == 0){
                Toast.makeText(activity,"??????????????????????????????",Toast.LENGTH_SHORT).show()
            }else{
                mPresenter.deleteCartGoods(list)
            }
        }

        //?????????????????????
        mSettleAccountsBtn.onClick {
            val list: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList
                .filter { it.isSelected }  //??????????????????
                .mapTo(list) { it } //???id??????list
            if(list.size == 0){
                Toast.makeText(activity,"??????????????????????????????",Toast.LENGTH_SHORT).show()
            }else{
                mPresenter.submitCartGoods(list, mTotalPrice)
            }
        }

    }

    //????????????
    private fun upDateTotalPrice(){
        mTotalPrice = mAdapter.dataList
            .filter { it.isSelected }  //??????????????????
            .map { it.goodsPrice * it.goodsCount } //?????? * ??????
            .sum() //??????
        mTotalPriceTv.text = "??????: ?? ${YuanFenConverter.changeF2Y(mTotalPrice)}"
    }

    fun showLeftIv(){
        mHeaderBar.getLeftView().setVisible(true)
    }

    override fun injectComponent() {
        DaggerCartComponent.builder()
            .activityComponent(activityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetCartGoodsResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0){
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVisible(true)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

            val isAllChecked = result.all { it.isSelected }
            mAllCheckedCb.isChecked = isAllChecked
        }else{
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        AppPrefsUtils.putInt(SP_CART_SIZE, result?.size?:0)
        Bus.send(UpdateCartSizeEvent())
    }

    override fun onDeleteCartGoodsResult(result: Boolean) {
        initData()
    }

    override fun onSubmitCartGoodsResult(result: Int) {
        ARouter.getInstance()
            .build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID, result)
            .navigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}