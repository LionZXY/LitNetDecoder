package models

class Genre(var id: Int,
            var name: String,
            var rating_place: String) {
    constructor() : this(0, "", "")
}