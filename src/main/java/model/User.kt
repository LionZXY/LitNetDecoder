package model

import com.google.gson.annotations.SerializedName
data class User(
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("balance")
        val balance: Double,
        @SerializedName("birthday")
        val birthday: Long,
        @SerializedName("can_comment")
        val canComment: Boolean,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_adult")
        val isAdult: Boolean,
        @SerializedName("is_child")
        val isChild: Boolean,
        @SerializedName("lang_content")
        val langContent: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("temporary_token_expiration_date")
        val temporaryTokenExpirationDate: Long,
        @SerializedName("temporary_token")
        val temporaryToken: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("top_img")
        val topImg: String){

}