package ru.lionzxy.litnetbot

import ru.lionzxy.litnetbot.core.RetrofitProvider

fun main(args: Array<String>) {
    val provider = RetrofitProvider()
    provider.setUserToken("s7eEW8jOhSGiqCkRPRNt5laY350g4rDe")
    val libraryApi = provider.getLibraryApi()
    /*libraryApi.get().map { list -> list.map { it.book } }
            .flatMap { getBookWithChapter(it, provider) }
            .subscribe({ list -> list.forEach { saveBook(it) } }, { println(it) })*/
}
