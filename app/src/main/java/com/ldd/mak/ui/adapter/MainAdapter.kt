package com.ldd.mak.ui.adapter

import android.content.Context
import android.widget.TextView
import com.ldd.base.ui.adapter.BaseListAdapter
import com.ldd.base.ui.adapter.BaseListVH
import com.ldd.mak.R

class MainAdapter(mContext: Context, list: List<String>) : BaseListAdapter<String>(mContext, list) {
    override fun getLayoutId()= R.layout.item_main

    override fun convert(vh: BaseListVH, position: Int, data: String) {
        val tvContent=vh.getView<TextView>(R.id.tvItemContent)
        tvContent.text=data
    }

}