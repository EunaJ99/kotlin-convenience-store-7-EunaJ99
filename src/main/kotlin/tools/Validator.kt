package tools

import store.RequiredProduct

class Validator {
    fun isSelectionFormatted(selection: String) {
        val selectionRule = """\[([가-힣a-zA-Z]+)-([0-9]+)]""".toRegex()
        if (!selection.matches(selectionRule)) {
            throw IllegalArgumentException(ErrorMessage.INPUT_NOT_CORRECT.getMessage())
        }
    }

    fun isProductExists(requirements: ArrayList<RequiredProduct>) {
        requirements.forEach {
            if (it.productNumber < 0) {
                throw IllegalArgumentException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage())
            }
        }
    }

    fun isStockEnough(required: Int, stock: Int) {
        if (stock > required) {
            throw IllegalArgumentException(ErrorMessage.PRODUCT_NOT_ENOUGH.getMessage())
        }
    }

    fun isYorN(answer: String) {
        val upperAnswer = answer.uppercase()
        val acceptedAnswer = listOf("Y", "N")
        if (!acceptedAnswer.contains(upperAnswer)) {
            throw IllegalArgumentException(ErrorMessage.INPUT_SOMETHING_WRONG.getMessage())
        }
    }
}