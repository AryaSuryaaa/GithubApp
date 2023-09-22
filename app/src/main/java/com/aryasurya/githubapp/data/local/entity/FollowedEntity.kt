package com.aryasurya.githubapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "follow")
@Parcelize
data class FollowedEntity(
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    val login: String,

    @field:ColumnInfo(name = "urlImg")
    val urlImg: String,

    @field:ColumnInfo(name = "isFollowed")
    var isFollowed: Boolean
): Parcelable