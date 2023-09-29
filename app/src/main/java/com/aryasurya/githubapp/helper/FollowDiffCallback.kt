package com.aryasurya.githubapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.aryasurya.githubapp.data.local.entity.Follow

class FollowDiffCallback(private val oldFollowList: List<Follow>, private val newFollowList: List<Follow>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFollowList.size

    override fun getNewListSize(): Int = newFollowList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFollowList[oldItemPosition].login == newFollowList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFollow = oldFollowList[oldItemPosition]
        val newFollow = newFollowList[newItemPosition]
        return oldFollow.login == newFollow.login && oldFollow.urlImg == newFollow.urlImg
    }

}