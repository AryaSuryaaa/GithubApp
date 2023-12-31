package com.aryasurya.githubapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class  DetailUserResponse(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: Any? = null,
)
