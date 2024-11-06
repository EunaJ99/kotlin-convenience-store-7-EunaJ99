package store

class Employee(private val storage: Storage) {
    private val productLedger = storage.getLedger()

    fun findMultipleProducts(product: ArrayList<RequiredProduct>): List<Int> {
        val productNumbers = ArrayList<Int>()
        product.forEach {
            productNumbers.add(findProduct(it.name))
        }
        return productNumbers.toList()
    }

    private fun findProduct(name: String): Int {
        var index = -1
        for (i in 0..productLedger.lastIndex) {
            if (productLedger[i].name == name) {
                index = i
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
}