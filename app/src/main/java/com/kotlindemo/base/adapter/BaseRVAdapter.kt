package com.kotlindemo.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


/************************************************************
 *
 *
 *                   .::::.
 *                  .::::::::.
 *                 :::::::::::  COME ON BABY
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *************************************************************
 * @author: 李刘欢
 * @date：2019/10/18 10:52
 * @version:1.0.0
 * @description: BaseRVAdapter
 */
abstract class BaseRVAdapter<T>(var mData: ArrayList<T>) : RecyclerView.Adapter<BaseRVViewHolder>() {
    private var mItemClickListener: OnItemClickListener? = null
    private var mItemLongClickListener: OnItemLongClickListener? = null

    fun setData(categoryList: ArrayList<T>) {
        this.mData.clear()
        this.mData = categoryList
        notifyDataSetChanged()
    }

    fun addData(dataList: ArrayList<T>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVViewHolder {
        val view = LayoutInflater.from(parent.context)?.inflate(layoutId(viewType), parent, false)
        return BaseRVViewHolder(view!!)
    }

    @LayoutRes
    protected abstract fun layoutId(viewType: Int): Int

    override fun onBindViewHolder(holderBaseRV: BaseRVViewHolder, position: Int) {
        bindData(holderBaseRV, mData[position], position)
        mItemClickListener?.let {
            holderBaseRV.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }
        mItemLongClickListener?.let {
            holderBaseRV.itemView.setOnLongClickListener {
                mItemLongClickListener!!.onItemLongClick(
                    mData[position],
                    position
                )
            }
        }
    }

    override fun onBindViewHolder(holderBaseRV: BaseRVViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holderBaseRV, position)
        } else {
            bindCacheData(holderBaseRV, mData[position], position)
        }
    }

    protected abstract fun bindData(holderBaseRV: BaseRVViewHolder, data: T, position: Int)

    protected fun bindCacheData(holderBaseRV: BaseRVViewHolder, data: T, position: Int) {

    }

    override fun getItemCount(): Int = mData.size

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener
    }

}