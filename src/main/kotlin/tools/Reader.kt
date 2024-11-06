package tools

import store.FilePath
import java.io.BufferedReader
import java.io.File

class Reader {
    private val product = File(FilePath.PRODUCTS.path)
    private val promotion = File(FilePath.PROMOTIONS.path)

    fun readProducts(): ArrayList<String> {
        val reader = product.reader()
        val readerBuffer = BufferedReader(reader)
        val rawProducts = ArrayList<String>()
        while (true) {
            val line = readerBuffer.readLine() ?: break
            rawProducts.add(line)
        }
        readerBuffer.close()
        reader.close()
        return rawProducts
    }

    fun readPromotions(): ArrayList<String> {
        val reader = promotion.reader()
        val readerBuffer = BufferedReader(reader)
        val rawPromotions = ArrayList<String>()
        while (true) {
            val line = readerBuffer.readLine() ?: break
            rawPromotions.add(line)
        }
        readerBuffer.close()
        reader.close()
        return rawPromotions
    }
}