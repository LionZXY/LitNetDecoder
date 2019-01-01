package model

import com.google.gson.annotations.SerializedName

data class AbstractUser(
        @SerializedName("about")
        var about: String = "",
        @SerializedName("birthday")
        var birthday: String = "",
        @SerializedName("email")
        var email: String = "",
        @SerializedName("id")
        var id: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("nickname")
        var nickname: String = "",
        @SerializedName("password")
        var password: String = "",
        @SerializedName("phoneVerifyCode")
        var phoneVerifyCode: String = "",
        @SerializedName("photo")
        var photo: String = "",
        @SerializedName("service")
        var service: String = "",
        @SerializedName("sex")
        var sex: String = "",
        @SerializedName("socialToken")
        var socialToken: String = "",
        @SerializedName("url")
        var url: String = "")