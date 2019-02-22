import com.adobe.dp.epub.io.OCFContainerWriter
import com.google.gson.GsonBuilder
import epub.EpubCreator
import model.book.Book
import model.book.BookChapter
import model.book.BookWrapper
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import java.util.zip.GZIPInputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    File("bookOut").mkdir()
    var books: List<Book> = emptyList()
    DriverManager.getConnection("jdbc:sqlite:in/library.db").use { connection ->
        books = getBooksFromDB(connection).onEach { it.chapters = getBookChapters(it, connection) }
    }
    books.forEach { saveBook(it) }
}

fun saveBook(book: Book) {
    book.chapters.forEach {
        try {
            it.text = getTextChapter(it.id)
        } catch (e: NoSuchFileException) {
            println("Не найдена часть ${it.title} для книги ${book.title}")
        }
    }

    val epubCreator = EpubCreator()
    val epub = epubCreator.getEpubFile(book)

    val filename = book.title.replace("[^a-zA-Zа-яА-Я0-9-_\\.]", "_")
    val writer = OCFContainerWriter(
            FileOutputStream("bookOut/$filename.epub"))
    epub.serialize(writer)
}

fun getBookChapters(book: Book, connection: Connection): ArrayList<BookChapter> {
    val gson = GsonBuilder().create()
    val statement = connection.createStatement()
    val chapterRS = statement.executeQuery("SELECT chapter_id, chapter FROM table_chapters WHERE book_id = ${book.id} ORDER BY priority")
    val chapters = ArrayList<BookChapter>()
    while (chapterRS.next()) {
        val chapterId = chapterRS.getInt("chapter_id")
        val chapterJson = chapterRS.getString("chapter")
        val chapter = gson.fromJson(chapterJson, BookChapter::class.java)
        chapter.id = chapterId
        chapters.add(chapter)
    }
    return chapters
}

fun getBooksFromDB(connection: Connection): List<Book> {
    val gson = GsonBuilder().create()
    val statement = connection.createStatement()
    val bookRS = statement.executeQuery("SELECT id, book FROM table_books;")
    val books = ArrayList<Book>()
    while (bookRS.next()) {
        val bookId = bookRS.getInt("id")
        val bookJson = bookRS.getString("book")
        val bookWrapper = gson.fromJson(bookJson, BookWrapper::class.java)
        val book = bookWrapper.book
        book.id = bookId.toLong()
        books.add(book)
    }
    return books
}

fun getTextChapter(chapterId: Int): String {
    val book = extractGz(File("in/chapters/$chapterId.gz"))
    val gson = GsonBuilder().create()
    val lines = gson.fromJson(book, Array<String>::class.java)
    val sb = StringBuilder()
    lines.forEach { sb.append(it) }
    return sb.toString()
}

fun extractGz(file: File): String {
    val cryptString = String(Files.readAllBytes(file.toPath()))
    val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
    instance.init(2, SecretKeySpec("14a6579a984b3c6abecda6c2dfa83a64".toByteArray(charset("UTF-8")), "AES"),
            IvParameterSpec(cryptString.toByteArray(Charset.forName("UTF-8")), 0, 16))
    val doFinal = instance.doFinal(Base64.getDecoder().decode(cryptString))
    val bArr = Arrays.copyOfRange(doFinal, 16, doFinal.size)

    val byteGZIPInputStream = GZIPInputStream(ByteArrayInputStream(bArr), 8192)
    val text = byteGZIPInputStream.reader().readText()
    return text
}