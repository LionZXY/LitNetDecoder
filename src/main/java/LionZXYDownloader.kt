import core.RetrofitProvider

fun main(args: Array<String>) {
    val provider = RetrofitProvider()
    provider.setUserToken("_NolFQCWMrNZ5nlZMbRbV8ulYajRG8vY")
    val test = provider.getLibraryApi().get().blockingFirst()
    println(test)
}