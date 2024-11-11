package store

import tools.Processer
import tools.Reader

class Storage {
    private val productLedger = ArrayList<ProductInfo>()

    fun openStorage() {
        val rawProducts = Reader().readProducts()
        splitProductInfo(rawProducts)
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
            Processer().processPrice(info[1]),
            processQuantity(info[2]),
            processPromotion(info[3])
        )
        return productInfo
    }

    private fun processName(name: String) = name.trim()

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

    fun findMultipleProducts(product: ArrayList<RequiredProduct>): ArrayList<RequiredProduct> {
        product.forEach {
            it.productNumber = findProduct(it.name)
        }
        return product
    }

    private fun findProduct(name: String): Int {
        var index = -1
        for (i in 0..productLedger.lastIndex) {
            if (productLedger[i].name == name) {
                index = i
                return index
            }
        }
        return index
    }

    fun findQuantity(index: Int): Int {
        val quantity = productLedger[index].quantity
        if (index != productLedger.lastIndex && productLedger[index].name == productLedger[index + 1].name) {
            return quantity + productLedger[index + 1].quantity
        }
        return quantity
    }

    fun sellProduct(index: Int, correction: Int) {
        if (productLedger[index].quantity < correction) {
            val temp = productLedger[index].quantity
            productLedger[index].quantity = 0
            productLedger[index + 1].quantity -= (correction - temp)
            return
        }
        productLedger[index].quantity -= correction
    }
}