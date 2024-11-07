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

    fun isQuantityEnough(foundQuantity: Int, requiredQuantity: Int) {
        if (requiredQuantity > foundQuantity) {
            throw IllegalArgumentException(ErrorMessage.PRODUCT_NOT_ENOUGH.getMessage())
        }
    }

    fun answerCheck(answer: String) {
        val acceptedAnswer = listOf("Y", "N", "y", "n")
        if (!acceptedAnswer.contains(answer)) {
            throw IllegalArgumentException(ErrorMessage.INPUT_SOMETHING_WRONG.getMessage())
        }
    }
}