import core.LitNetInterceptorAuth
import core.RetrofitProvider
import model.AbstractUser

fun main(args: Array<String>) {
    /*
    RetrofitProvider.getUserApi().authByLogin("nastya.afonina.98@bk.ru", "afonka27")
            .singleOrError()
            .doOnSuccess {
                LitNetInterceptorAuth.setUser(it.token)
            }
            .flatMap { RetrofitProvider.getUserApi().findByToken(it.token).singleOrError() }
            .subscribe({ println(it) }, { println(it) })*/
}
