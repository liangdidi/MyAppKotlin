package com.ldd.mak.ui.adapter.list

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldd.base.ui.adapter.BaseRVAdapter
import com.ldd.base.ui.adapter.BaseRVVH
import com.ldd.mak.R

class NestList1Adapter(private var mContext: Context, list: List<List<List<String>>>) : BaseRVAdapter<List<List<String>>>(mContext, list) {
    override fun getLayoutId(viewType: Int)= R.layout.item_nest_list1

    override fun convert(vh: BaseRVVH, position: Int, data: List<List<String>>) {
        Log.i("ldd","=========ListAdapter1==========position=${position}")
        val tvContent:TextView=vh.getView(R.id.tv_item_list1_content)
        val rv:RecyclerView=vh.getView(R.id.recyclerView_item_list1)

        tvContent.text="2020-0${position+1}"
        rv.layoutManager=LinearLayoutManager(mContext)
        rv.adapter=NestList2Adapter(mContext,data)
    }
}