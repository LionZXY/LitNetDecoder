import core.RetrofitProvider

fun main(args: Array<String>) {
    /*RetrofitProvider().getUserApi().authByLogin("nikita@kulikof.ru", "159357za")
            .singleOrError()
            .subscribe({ println(it) }, { println(it) })*/

    val provider = RetrofitProvider()
    provider.setUserToken("s7eEW8jOhSGiqCkRPRNt5laY350g4rDe")
    provider.getNoticedApi().noticedGet().subscribe({
        println(it)
    }, {
        println(it)
    })
}
