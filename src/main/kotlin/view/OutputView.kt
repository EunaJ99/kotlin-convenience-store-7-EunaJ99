package view

import store.ProductInfo

class OutputView {
    fun printGreetings() {
        println(GREETINGS)
    }

    fun printProducts(productLedger: ArrayList<ProductInfo>) {
        println()
        for (i in 0..productLedger.lastIndex) {
            println(printSingleProductInfo(productLedger[i]))
        }
        println()
    }

    private fun printSingleProductInfo(productInfo: ProductInfo): String {
        var printableQuantity = "%d개".format(productInfo.quantity)
        if (productInfo.quantity == 0) {
            printableQuantity = "재고 없음"
        }
        val formatted = "- %s %s원 %s %s".format(productInfo.name, productInfo.price, printableQuantity, productInfo.promotion)
        return formatted
    }

    companion object {
        const val GREETINGS = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."
    }
}