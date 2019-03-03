import core.RetrofitProvider

fun main(args: Array<String>) {
    RetrofitProvider().getUserApi().authByLogin("nikita@kulikof.ru", "159357za")
            .singleOrError()
            .subscribe({ println(it) }, { println(it) })

}
