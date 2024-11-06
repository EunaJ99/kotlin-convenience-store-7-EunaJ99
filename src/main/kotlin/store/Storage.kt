package store

import tools.Reader
import java.text.DecimalFormat

class Storage {
    private val productLedger = ArrayList<ProductInfo>()

    fun openStorage(): ArrayList<ProductInfo> {
        val rawProducts = Reader().readProducts()
        splitProductInfo(rawProducts)
        return productLedger
    }

    fun getLedger(): ArrayList<ProductInfo> = productLedger

    private fun splitProductInfo(rawProducts: ArrayList<String>) {
        for (i in 1..rawProducts.lastIndex) {
            val info = rawProducts[i].split(",")
            val productInfo = processInfo(info)
            productLedger.add(productInfo)
            checkNormalProducts()
        }
    }

    private fun processInfo(info: List<String>): ProductInfo {
        val productInfo = ProductInfo(
            processName(info[0]),
            processPrice(info[1]),
            processQuantity(info[2]),
            processPromotion(info[3])
        )
        return productInfo
    }

    private fun processName(name: String) = name.trim()

    private fun processPrice(price: String): String {
        val priceNumber = price.toInt()
        val priceFormat = DecimalFormat("#,###")
        return priceFormat.format(priceNumber)
    }

    private fun processQuantity(quantity: String) = quantity.trim().toInt()

    private fun processPromotion(promotions: String): String {
        if (promotions == "null") {
            return ""
        }
        return promotions
    }

    private fun checkNormalProducts() {
        val index = productLedger.lastIndex
        if (index == 0) {
            return
        }
        if (productLedger[index].name != productLedger[index - 1].name && productLedger[index - 1].promotion != "") {
            val temp = productLedger[index]
            productLedger[index] = getNormalProducts(productLedger[index - 1])
            productLedger.add(temp)
        }
    }

    private fun getNormalProducts(product: ProductInfo): ProductInfo {
        val normalProduct = ProductInfo(
            product.name,
            product.price,
            0,
            ""
        )
        return normalProduct
    }

}