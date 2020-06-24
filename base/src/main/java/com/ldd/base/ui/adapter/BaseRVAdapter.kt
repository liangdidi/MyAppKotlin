package com.ldd.base.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 基本的RecyclerView适配器
 */
abstract class BaseRVAdapter<T>(
    val mContext: Context,
    val list: List<T>
) : RecyclerView.Adapter<BaseRVVH>() {

    //子类实现
    abstract fun getLayoutId(viewType: Int): Int
    abstract fun convert(vh: BaseRVVH, position: Int, data: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVVH {
        return BaseRVVH(
            LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false)
        )
    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(vh: BaseRVVH, position: Int) {
        convert(vh, position, list[position])
    }
}