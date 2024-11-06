package controller

import store.Storage
import view.View

class Controller(private val view: View) {
    private val storage = Storage()

    fun runBusiness() {
        val selection = openStore()
    }

    private fun openStore(): String {
        val ledger = storage.getProducts()
        val selection = view.welcomeCustomer(ledger)
        return selection
    }
}