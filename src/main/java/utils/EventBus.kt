package utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import model.book.Book

class EventBus {
    companion object {
        val publisher: PublishSubject<Book> = PublishSubject.create()

        inline fun getObservable(): Observable<Book> {
            return publisher
        }

        fun post(event: Book) {
            publisher.onNext(event)
        }
    }
}