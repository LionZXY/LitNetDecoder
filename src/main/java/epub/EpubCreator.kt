package epub

import org.slf4j.LoggerFactory
import java.io.File

const val js = "new EpubMaker().makeEpub().then(function(epubZipContent) {sendEpubToJava(epubZipContent)});"

class EpubCreator {
    private val logger = LoggerFactory.getLogger(EpubCreator::class.java)

    fun getEpubFile() {

    }


}