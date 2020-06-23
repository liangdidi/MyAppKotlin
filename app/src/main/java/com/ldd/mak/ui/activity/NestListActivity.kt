package com.ldd.mak.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.ldd.mak.R
import com.ldd.mak.ui.adapter.list.NestList1Adapter
import kotlinx.android.synthetic.main.ac_nest_list.*

/**
 * 嵌套列表
 */
class NestListActivity : BaseActivity() {
    override fun isWantTitleBar() = true

    override fun getLayoutId() = R.layout.ac_nest_list

    override fun initData() {

        val list = listOf(
            listOf(
                listOf("", "", "", ""),
                listOf("", "")
            ),
            listOf(
                listOf("", ""),
                listOf("", "", ""),
                listOf(""),
                listOf("", "", "")
            ),
            listOf(
                listOf("", "","","","","")
            ),
            listOf(
                listOf("", "","","","",""),
                listOf("", "", ""),
                listOf("", "", "")
            )
            ,
            listOf(
                listOf("", "","","","",""),
                listOf("", "", ""),
                listOf("", "", "")
            ),
            listOf(
                listOf("", "","","","",""),
                listOf("", "", ""),
                listOf("", "", "")
            ),
            listOf(
                listOf("", "","","","",""),
                listOf("", "", ""),
                listOf("", "", "")
            )
        )

        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = NestList1Adapter(mContext, list)
        recyclerView.setHasFixedSize(true);
        recyclerView.isNestedScrollingEnabled = false;
    }




}