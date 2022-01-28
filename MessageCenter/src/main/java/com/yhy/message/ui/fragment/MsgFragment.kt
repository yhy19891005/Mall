package com.yhy.message.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.yhy.base.ext.startLoading
import com.yhy.base.rx.Bus
import com.yhy.base.rx.registerInBus
import com.yhy.base.ui.fragment.BaseMvpFragment
import com.yhy.message.R
import com.yhy.message.data.protocol.Message
import com.yhy.message.event.MessageBridgeEvent
import com.yhy.message.injection.component.DaggerMsgComponent
import com.yhy.message.injection.module.MsgModule
import com.yhy.message.presenter.MsgPresenter
import com.yhy.message.presenter.view.MsgView
import com.yhy.message.ui.adapter.MsgAdapter
import com.yhy.provider.event.LoginSucEvent
import kotlinx.android.synthetic.main.fragment_message.*

class MsgFragment: BaseMvpFragment<MsgPresenter>(), MsgView {

    private lateinit var mAdapter: MsgAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_message, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        activity?.let {
            val manager = LinearLayoutManager(it)
            manager.orientation = LinearLayoutManager.VERTICAL
            mMessageRv.layoutManager = manager
            mAdapter = MsgAdapter(it)
            mMessageRv.adapter = mAdapter
        }
    }

    private fun initData() {
        mMultiStateView.startLoading()
        mPresenter.getMessageList()
    }

    private fun initListener() {
        Bus.observe<LoginSucEvent>()
            .subscribe {

                run {
                    initData()
                }
            }
            .registerInBus(this)
    }

    override fun injectComponent() {
        DaggerMsgComponent.builder()
            .activityComponent(activityComponent)
            .msgModule(MsgModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetMsgResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0){
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            Bus.send(MessageBridgeEvent(false))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}