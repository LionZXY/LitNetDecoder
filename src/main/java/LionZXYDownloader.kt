import core.LitNetInterceptorAuth
import core.RetrofitProvider
import epub.EpubCreator

fun main(args: Array<String>) {
    val provider = RetrofitProvider()
    LitNetInterceptorAuth.setUser("_NolFQCWMrNZ5nlZMbRbV8ulYajRG8vY")
    val test = provider.getLibraryApi().get().blockingFirst()
    println(test)
}