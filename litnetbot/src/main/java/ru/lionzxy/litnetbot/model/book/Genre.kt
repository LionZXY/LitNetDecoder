package ru.lionzxy.litnetbot.model.book

import com.google.gson.annotations.SerializedName

class Genre(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("rating_place")
        var ratingPlace: String) {
    constructor() : this(0, "", "")
}