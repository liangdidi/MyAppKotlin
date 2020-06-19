package com.ldd.mak.ui.adapter.list

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.ldd.base.ui.adapter.BaseRVAdapter
import com.ldd.base.ui.adapter.BaseRVVH
import com.ldd.mak.R

class NestList3Adapter(mContext: Context, list: List<String>) : BaseRVAdapter<String>(mContext, list) {
    override fun getLayoutId(viewType: Int)= R.layout.item_nest_list3

    override fun convert(vh: BaseRVVH, position: Int, data: String) {
        Log.i("ldd","=========List3Adapter==========position=${position}")
        val tvContent:TextView=vh.getView(R.id.tv_item_list3_content)
        tvContent.text="$position 详情"
    }
}