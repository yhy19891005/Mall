package com.yhy.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhy.base.ext.setVisible
import com.yhy.base.ui.activity.BaseActivity
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.goods.R
import com.yhy.goods.common.GOODS_KEY_WORD
import com.yhy.goods.common.SP_SEARCH_HISTORY
import com.yhy.goods.ui.adapter.SearchHistoryAdapter
import kotlinx.android.synthetic.main.activity_search_goods.*

class SearchGoodsActivity: BaseActivity(), View.OnClickListener {

    lateinit var mAdapter: SearchHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_goods)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        mSearchHistoryRv.layoutManager = manager
        mAdapter = SearchHistoryAdapter(this)
        mSearchHistoryRv.adapter = mAdapter

        mLeftIv.setOnClickListener(this)
        mSearchTv.setOnClickListener(this)
        mClearBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        val set = AppPrefsUtils.getStringSet(SP_SEARCH_HISTORY)
        set?.let {
            mNoDataTv.setVisible(it.isEmpty())
            mDataView.setVisible(it.isNotEmpty())
            if (it.isNotEmpty()) {
                val list = mutableListOf<String>()
                list.addAll(it)
                mAdapter.setData(list)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            mLeftIv ->{ finish() }
            mSearchTv ->{ search() }
            mClearBtn ->{ clearSearchHistory() }
        }
    }

    private fun search(){
        val keyWord = mKeywordEt.text.toString().trim()
        if(TextUtils.isEmpty(keyWord)){
            Toast.makeText(this@SearchGoodsActivity, "请输入搜索内容", Toast.LENGTH_SHORT).show()
            return
        }
        AppPrefsUtils.putStringSet(SP_SEARCH_HISTORY, mutableSetOf(keyWord))
        val intent = Intent(this@SearchGoodsActivity, KeyWordGoodsActivity::class.java)
        intent.putExtra(GOODS_KEY_WORD, keyWord)
        startActivity(intent)
    }

    private fun clearSearchHistory(){
        AppPrefsUtils.remove(SP_SEARCH_HISTORY)
        loadSearchHistory()
    }
}