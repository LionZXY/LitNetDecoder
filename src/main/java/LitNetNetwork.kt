import core.RetrofitProvider

fun main(args: Array<String>) {
    RetrofitProvider.getRegisterApi().signUpDevice().subscribe { println(it) }
}