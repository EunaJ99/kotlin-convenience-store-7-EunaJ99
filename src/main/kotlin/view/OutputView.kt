package view

import store.ProductFinalInfo
import store.ProductInfo
import store.ReceiptInfo
import tools.Processer

class OutputView {
    val processer = Processer()

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

    fun printReceipt(receiptInfo: ReceiptInfo) {
        println(RECEIPT_HEADER)
        receiptInfo.products.forEach {
            printRequestedProducts(it)
        }
        println(RECEIPT_FREE_HEADER)
        receiptInfo.products.forEach {
            printFreeProducts(it)
        }
        println(RECEIPT_DIVIDER)
        printFooter(receiptInfo)
    }

    private fun printRequestedProducts(info: ProductFinalInfo) {
        val name = info.name
        val count = info.quantity
        val price = processer.processPrice(info.price.toString())
        println(RECEIPT_PRODUCTS.format(name, count, price))
    }

    private fun printFreeProducts(info: ProductFinalInfo) {
        if (info.free > 0) {
            val name = info.name
            val count = info.free
            println(RECEIPT_PRODUCTS.format(name, count, ""))
        }
    }

    private fun printFooter(info: ReceiptInfo) {
        val totalPrice = processer.processPrice(info.totalPrice.toString())
        val totalDiscount = processer.processPrice(info.totalDiscount.toString())
        val membership = processer.processPrice(info.membership.toString())
        val priceToPay = processer.processPrice(info.priceToPay.toString())
        println(RECEIPT_FOOTER.format(info.totalQuantity, totalPrice, totalDiscount, membership, priceToPay))
    }

    companion object {
        const val GREETINGS = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."
        const val RECEIPT_HEADER = "==============W 편의점================\n상품명\t\t수량\t\t금액"
        const val RECEIPT_PRODUCTS = "%s\t\t%d \t\t%s"
        const val RECEIPT_FREE_HEADER = "=============증\t정==============="
        const val RECEIPT_DIVIDER = "===================================="
        const val RECEIPT_FOOTER = "총구매액\t\t%d\t\t%s\n" +
                "행사할인\t\t\t\t%s\n" +
                "멤버십할인\t\t\t%s\n" +
                "내실돈\t\t\t\t%s"
    }
}