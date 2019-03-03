import com.adobe.dp.epub.io.OCFContainerWriter
import core.RetrofitProvider
import epub.EpubCreator
import io.reactivex.Observable
import model.book.Book
import utils.LitNetDecode
import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val provider = RetrofitProvider()
    provider.setUserToken("s7eEW8jOhSGiqCkRPRNt5laY350g4rDe")
    val libraryApi = provider.getLibraryApi()
    libraryApi.get().map { list -> list.map { it.book } }
            .flatMap { getBookWithChapter(it, provider) }
            .subscribe({ list -> list.forEach { saveBook(it) } }, { println(it) })
}

fun getBookWithChapter(books: List<Book>, provider: RetrofitProvider): Observable<List<Book>> {
    val bookApi = provider.getBookApi()
    val bookMap = books.map { it.id to it }.toMap()
    return bookApi.getContentsByIds(books.map { it.id }).map { list ->
        list.map { it.contents }.flatten()
    }.flatMap { list ->
        val chaptersMap = list.map { it.id to it }.toMap()
        list.map { it.id }.chunked(100).map {
            bookApi.getCahpters(it)
        }.reduce { el1, el2 -> el1.flatMap { items -> el2.map { it.plus(items) } } }.map { list ->
            list.forEach {
                chaptersMap[it.id]!!.text = LitNetDecode.parseAndEncode(it.text)
            }
        }.map { list }
    }.doOnNext { list ->
        list.forEach {
            bookMap[it.bookId]!!.chapters.add(it)
        }
    }.map { _ ->
        books.forEach { book -> book.chapters.sortedBy { it.priority } }
        books
    }
}

fun saveBook(book: Book) {
    val epubCreator = EpubCreator()
    val epub = epubCreator.getEpubFile(book)

    val filename = book.title.replace("[^a-zA-Zа-яА-Я0-9-_\\.]", "_")
    val file = File("bookOut/$filename.epub")
    file.parentFile.mkdirs()
    file.createNewFile()
    val writer = OCFContainerWriter(
            FileOutputStream(file))
    epub.serialize(writer)
}
