package tools

class Validator {
    fun isSelectionFormatted(selection: String) {
        val selectionRule = """\[([가-힣a-zA-Z]+)-([0-9]+)]""".toRegex()
        if (!selection.matches(selectionRule)) {
            throw IllegalArgumentException(ErrorMessage.INPUT_NOT_CORRECT.getMessage())
        }
    }

    fun isProductExists(productNumbers: List<Int>) {
        if (productNumbers.contains(-1)) {
            throw IllegalArgumentException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage())
        }
    }

    fun isQuantityEnough(foundQuantity: Int, requiredQuantity: Int) {
        if (requiredQuantity > foundQuantity) {
            throw IllegalArgumentException(ErrorMessage.PRODUCT_NOT_ENOUGH.getMessage())
        }
    }

    fun answerCheck(answer: String) {
        if (answer != "Y" && answer != "N") {
            throw IllegalArgumentException(ErrorMessage.INPUT_SOMETHING_WRONG.getMessage())
        }
    }
}