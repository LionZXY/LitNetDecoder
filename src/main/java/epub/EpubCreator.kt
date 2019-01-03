package epub

import com.adobe.dp.epub.ncx.TOCEntry
import com.adobe.dp.epub.opf.NCXResource
import com.adobe.dp.epub.opf.Publication
import com.adobe.dp.epub.ops.Element
import com.adobe.dp.epub.ops.OPSDocument
import models.Book
import models.BookChapter
import org.jsoup.Jsoup
import org.jsoup.nodes.TextNode
import org.slf4j.LoggerFactory
import utils.InputDataSource
import java.net.URL
import java.util.*

typealias JsoupElement = org.jsoup.nodes.Element

class EpubCreator {
    private val logger = LoggerFactory.getLogger(EpubCreator::class.java)

    public fun getEpubFile(book: Book): Publication {
        val epub = Publication()

        epub.addDCMetadata("title", book.title)
        epub.addDCMetadata("date", Date(book.created_at).toString())
        epub.addDCMetadata("description", book.annotation)
        epub.addDCMetadata("creator", book.author_name)
        epub.addDCMetadata("author", book.author_name)


        val dataSource = InputDataSource(URL(book.cover).openStream())
        val res = epub.createBitmapImageResource("OPS/cover.jpg", "image/jpeg", dataSource)
        epub.coverImage = res


        // prepare table of contents
        val toc = epub.toc
        val rootTOCEntry = toc.rootTOCEntry


        book.chapters.forEach {
                addChapter(it, epub, toc, rootTOCEntry)

        }
        return epub
    }

    private fun addChapter(chapter: BookChapter, epub: Publication,
                           toc: NCXResource, rootTOCEntry: TOCEntry) {
        // create new chapter resource
        val main = epub.createOPSResource("OPS/chapter${chapter.id}.xhtml")
        epub.addToSpine(main)

        // get chapter document
        val mainDoc = main.document

        // add chapter to the table of contents
        val mainTOCEntry = toc.createTOCEntry(chapter.title, mainDoc
                .rootXRef)
        rootTOCEntry.add(mainTOCEntry)

        // chapter XHTML body element
        val body = mainDoc.body

        // add a header
        val elements = extractElementFromText(chapter.text ?: "Нет главы", mainDoc)
        elements.content().forEach { body.add(it) }
    }

    private fun extractElementFromText(text: String, doc: OPSDocument): Element {
        val root = Jsoup.parse(text).body()
        val body = recurcsionExtractElement(root, doc)
        return body
    }

    private fun recurcsionExtractElement(element: JsoupElement, doc: OPSDocument): Element {
        val htmlElement = doc.createElement(element.tagName())
        element.childNodes().forEach {
            when (it) {
                is TextNode -> htmlElement.add(it.wholeText)
                is JsoupElement -> htmlElement.add(recurcsionExtractElement(it, doc))
                else -> logger.warn("Not processing: $it ")
            }
        }
        return htmlElement
    }
}