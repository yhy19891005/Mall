package com.yhy.goods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.yhy.base.ext.startLoading
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.goods.R
import com.yhy.goods.common.GOODS_ID
import com.yhy.goods.data.protocol.Goods
import com.yhy.goods.injection.component.DaggerGoodsComponent
import com.yhy.goods.injection.module.GoodsModule
import com.yhy.goods.presenter.GoodsPresenter
import com.yhy.goods.presenter.view.GoodsView
import com.yhy.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.activity_goods.mMultiStateView


class GoodsActivity : BaseMvpActivity<GoodsPresenter>(), GoodsView, BGARefreshLayout.BGARefreshLayoutDelegate {

    lateinit var mAdapter: GoodsAdapter
    //当前页面
    private var mCurrentPage = 1
    //最大页面数
    private var mMaxPage = 1
    //商品categoryId
    private var mCategoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        initView()
        initRefreshLayout()
        initData()
        loadData()
    }

    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this, 2)
        mAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Goods>{
            override fun onItemClick(item: Goods, position: Int) {
                val intent = Intent(this@GoodsActivity, GoodsDetailActivity::class.java)
                intent.putExtra(GOODS_ID,item.id)
                startActivity(intent)
            }
        })
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun initData() {
        mCategoryId = intent.getIntExtra(GOODS_ID, 1)
    }

    private fun loadData(currentPage: Int = 1){
        mMultiStateView.startLoading()
        mPresenter.getGoodsList(mCategoryId, currentPage)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder()
            .activityComponent(activityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetGoodsResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()

        if( mCurrentPage == 1){
            if (result != null && result.size > 0) {
                mMaxPage = result[0].maxPage
                mAdapter.setData(result)
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            } else {
                //没有数据
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            }
        }else{
            if (result != null && result.size > 0){
                mAdapter.dataList.addAll(result)
                mAdapter.notifyDataSetChanged()
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }else{
                Toast.makeText(this@GoodsActivity, "没有更多了", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
      下拉加载第一页
   */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

    /*
       上拉加载更多
    */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData(mCurrentPage)
            true
        } else {
            false
        }
    }

}