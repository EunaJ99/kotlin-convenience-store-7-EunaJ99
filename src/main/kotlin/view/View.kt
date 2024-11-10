package view

import store.ProductInfo
import store.ReceiptInfo

class View {
    private val inputView = InputView()
    private val outputView = OutputView()

    fun welcomeCustomer(productLedger: ArrayList<ProductInfo>): String {
        outputView.printGreetings()
        outputView.printProducts(productLedger)
        val customerSelection = inputView.readItem()
        return customerSelection
    }

    fun giveawayOffer(name: String, quantity: Int): String {
        val answer = inputView.readPromotionFill(name, quantity)
        return answer
    }

    fun notFreeNotice(name: String, excess: Int): String {
        val answer = inputView.readIsRegular(name, excess)
        return answer
    }

    fun membershipDiscount(): String {
        val answer = inputView.readIsMembership()
        return answer
    }

    fun receipt(receiptInfo: ReceiptInfo) {
        outputView.printReceipt(receiptInfo)
    }

    fun startOver(): String {
        val answer = inputView.readIsContinue()
        return answer
    }
}