package com.ldd.mak.ui.adapter.list

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldd.base.ui.adapter.BaseRVAdapter
import com.ldd.base.ui.adapter.BaseRVVH
import com.ldd.mak.R

class NestList2Adapter(mContext: Context, list: List<List<String>>) : BaseRVAdapter<List<String>>(mContext, list) {
    override fun getLayoutId(viewType: Int)= R.layout.item_nest_list2

    override fun convert(vh: BaseRVVH, position: Int, data: List<String>) {
        Log.i("ldd","=========ListAdapter222222==========position=${position}")
        val llTitle:LinearLayout=vh.getView(R.id.ll_item_list2_title)
        val tvContent:TextView=vh.getView(R.id.tv_item_list2_content)
        val ivArrow:ImageView=vh.getView(R.id.iv_item_list2_arrow)
        val rv:RecyclerView=vh.getView(R.id.recyclerView_item_list2)
        tvContent.text="$position 时间：2020-06-19"

        llTitle.setOnClickListener {
            if(rv.visibility== View.GONE){
                rv.visibility= View.VISIBLE
                ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }else{
                rv.visibility= View.GONE
                ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
        rv.layoutManager= LinearLayoutManager(mContext)
        rv.adapter=NestList3Adapter(mContext,data)
    }
}