package controller

import store.Employee
import store.RequiredProduct
import store.Storage
import tools.Processer
import tools.Validator
import view.View

class Controller(private val view: View) {
    private val storage = Storage()
    private val validator = Validator()
    private val processer = Processer()
    private val employee = Employee(storage)

    fun runBusiness() {
        val selection = openStore()
        validateSelection(selection)
    }

    private fun openStore(): String {
        val ledger = storage.openStorage()
        val selection = view.welcomeCustomer(ledger)
        return selection
    }

    private fun validateSelection(selection: String) {
        val chopSelection = processer.chopRequirementInput(selection)
        chopSelection.forEach {
            validator.isSelectionFormatted(it)
        }
        val selectedProducts = processer.processRequirement(selection)
        val productNumbers = employee.findMultipleProducts(selectedProducts)
        validator.isProductExists(productNumbers)
        for (i in 0..selectedProducts.lastIndex) {
            validator.isQuantityEnough(employee.findQuantity(productNumbers[i]), selectedProducts[i].quantity)
        }
    }
}