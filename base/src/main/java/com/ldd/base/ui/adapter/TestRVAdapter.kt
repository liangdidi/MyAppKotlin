package com.ldd.base.ui.adapter

import android.content.Context
import android.widget.TextView
import com.ldd.base.R

/**
 * 测试使用
 */
class TestRVAdapter(mContext: Context, list: List<String>) : BaseRVAdapter<String>(mContext, list) {
    override fun getLayoutId(viewType: Int)= R.layout.item_test
    override fun convert(vh: BaseRVVH, position: Int, data: String) {
        val tvContent=vh.getView<TextView>(R.id.tvItemTestContent)
        tvContent.text=data
    }
}