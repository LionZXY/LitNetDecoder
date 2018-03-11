package models

class BookWrapper(var book: Book) {
    constructor() : this(Book())
}