package com.yhy.goods.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.yhy.base.ext.setVisible
import com.yhy.base.ext.startLoading
import com.yhy.base.ui.adapter.BaseRecyclerViewAdapter
import com.yhy.base.ui.fragment.BaseMvpFragment
import com.yhy.goods.R
import com.yhy.goods.common.GOODS_ID
import com.yhy.goods.data.protocol.Category
import com.yhy.goods.injection.component.DaggerCategoryComponent
import com.yhy.goods.injection.module.CategoryModule
import com.yhy.goods.presenter.CategoryPresenter
import com.yhy.goods.presenter.view.CategoryView
import com.yhy.goods.ui.activity.GoodsActivity
import com.yhy.goods.ui.adapter.SecondCategoryAdapter
import com.yhy.goods.ui.adapter.TopicCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment: BaseMvpFragment<CategoryPresenter>(), CategoryView {

    //一级分类Adapter
    lateinit var topAdapter: TopicCategoryAdapter
    //二级分类Adapter
    lateinit var secondAdapter: SecondCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        context?.let {
            val topicManager = LinearLayoutManager(it)
            topicManager.orientation = LinearLayoutManager.VERTICAL
            mTopCategoryRv.layoutManager = topicManager
            topAdapter = TopicCategoryAdapter(it)
            mTopCategoryRv.adapter = topAdapter
            topAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Category>{
                override fun onItemClick(item: Category, position: Int) {
                    for (category in topAdapter.dataList) {
                        category.isSelected = item.id == category.id
                    }
                    topAdapter.notifyDataSetChanged()
                    initData(item.id)
                }
            })

            mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
            secondAdapter = SecondCategoryAdapter(it)
            mSecondCategoryRv.adapter = secondAdapter
            secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
                override fun onItemClick(item: Category, position: Int) {
                    val intent = Intent(activity,GoodsActivity::class.java)
                    intent.putExtra(GOODS_ID,item.id)
                    startActivity(intent)
                }
            })
        }

    }

    private fun initData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)
    }

    override fun injectComponent() {
        DaggerCategoryComponent.builder()
            .activityComponent(activityComponent)
            .categoryModule(CategoryModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result != null && result.size > 0) {
            if (result[0].parentId == 0) {
                result[0].isSelected = true
                topAdapter.setData(result)
                mPresenter.getCategory(result[0].id)
            } else {
                secondAdapter.setData(result)
                mTopCategoryIv.setVisible(true)
                mCategoryTitleTv.setVisible(true)
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        } else {
            //没有数据
            mTopCategoryIv.setVisible(false)
            mCategoryTitleTv.setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
}