package ru.lionzxy.litnetbot.model.notify

import com.google.gson.annotations.SerializedName

data class NotifyObject(
        @SerializedName("id")
        var id: Int,
        @SerializedName("object_type")
        var objectType: NotifyType,
        @SerializedName("object_name")
        var objectName: String,
        @SerializedName("updated_at")
        var updateAt: Long,
        @SerializedName("read")
        var read: Boolean,
        @SerializedName("text")
        var text: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("img")
        var img: String? = null)