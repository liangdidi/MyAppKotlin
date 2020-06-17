package com.ldd.mak.ui.activity

import com.ldd.mak.R
import com.ldd.mak.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun isWantTitleBar()=true
    override fun getLayoutId()= R.layout.activity_main
    override fun initData() {
        setTitleBarName("首页")

        val list= listOf("1、权限申请")
        listView.adapter= MainAdapter(mContext,list)
        listView.setOnItemClickListener { parent, view, position, id ->
            val content=parent.getItemAtPosition(position)
            when(content){
                "权限申请"-> {
                    startActivityCustom(PermissionActivity::class.java)
                }
            }
        }

    }
}