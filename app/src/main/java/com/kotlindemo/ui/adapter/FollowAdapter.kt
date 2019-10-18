package com.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.R
import com.kotlindemo.base.adapter.BaseRVAdapter
import com.kotlindemo.base.adapter.BaseRVViewHolder
import com.kotlindemo.mode.HomeBean
import com.kotlindemo.util.ImageLoader


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
 * @date：2019/10/18 15:31
 * @version:1.0.0
 * @description: FollowAdapter
 */
class FollowAdapter(private val mContext: Context, mData: ArrayList<HomeBean.Issue.Item>) :
    BaseRVAdapter<HomeBean.Issue.Item>(mData) {

    override fun layoutId(viewType: Int): Int = R.layout.item_follow_layout

    override fun bindData(holderBaseRV: BaseRVViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            data.type == "videoCollectionWithBrief" -> setAuthorInfo(data, holderBaseRV)
        }
    }

    private fun setAuthorInfo(item: HomeBean.Issue.Item, holder: BaseRVViewHolder) {
        val headerData = item.data?.header
        ImageLoader.loadImage(mContext, headerData?.icon!!, holder.getView(R.id.iv_avatar), R.mipmap.default_avatar)

        holder.setText(R.id.tv_title, headerData.title)
        holder.setText(R.id.tv_desc, headerData.description)

        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = FollowHorizontalAdapter(mContext, item.data.itemList)
    }
}