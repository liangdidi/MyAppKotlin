package com.ldd.base.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * 基本适配器，缓存处理
 * 适用于ListView,GridView
 */
abstract class BaseListAdapter<T>(
    val mContext: Context,
    val list: List<T>
) : BaseAdapter() {

    //子类实现
    abstract fun getLayoutId(): Int
    abstract fun convert(vh: BaseListVH, position: Int, data: T)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val baseViewHolder = BaseListVH()
            .get(mContext, getLayoutId(), convertView, parent)
        convert(baseViewHolder, position, getItem(position))
        return baseViewHolder.mConvertView
    }

    override fun getItem(position: Int) = list[position]
    override fun getItemId(position: Int) = position.toLong()
    override fun getCount() = list.size
}