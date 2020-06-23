package com.ldd.mak.ui.activity

import com.ldd.mak.R
import com.ldd.mak.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")

        val list= listOf("1、权限申请","2、列表嵌套")
        listView.adapter= MainAdapter(mContext,list)

        listView.setOnItemClickListener { parent, view, position, id ->
            when(parent.getItemAtPosition(position)){
                "1、权限申请"-> startActivityCustom(PermissionActivity::class.java)
                "2、列表嵌套"-> startActivityCustom(NestListActivity::class.java)
            }
        }

    }
}