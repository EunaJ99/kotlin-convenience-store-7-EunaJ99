package view

import controller.Controller
import store.ProductInfo

class View {
    private val inputView = InputView()
    private val outputView = OutputView()
    private val controller = Controller(this)

    fun start() {
        while (true) {
            try {
                controller.runBusiness()
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
    }

    fun welcomeCustomer(productLedger: ArrayList<ProductInfo>): String {
        outputView.printGreetings()
        outputView.printProducts(productLedger)
        val customerSelection = inputView.readItem()
        return customerSelection
    }
}