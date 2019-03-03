package bot.cmd.helpers

import com.adobe.dp.epub.io.OCFContainerWriter
import core.RetrofitProvider
import db.TelegramUser
import epub.EpubCreator
import io.reactivex.Observable
import model.book.Book
import utils.LitNetDecode
import java.io.File
import java.io.FileOutputStream

const val OUTPUT_DIR = "tmp"

class DownloadHelper() {
    fun downloadBook(book: Book, provider: RetrofitProvider, user: TelegramUser): File {
        val filename = book.title.replace("[^a-zA-Zа-яА-Я0-9-_\\.]".toRegex(), "_")
        val file = File("$OUTPUT_DIR/${user.id.value}", "$filename.epub")

        val bookWithChapter = getBookWithChapter(listOf(book), provider).singleOrError().blockingGet().first()
        saveBook(bookWithChapter, file)

        return file
    }

    private fun getBookWithChapter(books: List<Book>, provider: RetrofitProvider): Observable<List<Book>> {
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

    private fun saveBook(book: Book, output: File) {
        val epubCreator = EpubCreator()
        val epub = epubCreator.getEpubFile(book)

        output.parentFile.mkdirs()
        output.createNewFile()
        val writer = OCFContainerWriter(
                FileOutputStream(output))
        epub.serialize(writer)
    }
}