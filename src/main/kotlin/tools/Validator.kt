package tools

class Validator {
    fun selectionCheck(selection: String) {
        val selectionRule = "([가-힣a-zA-Z]+)-([0-9]+)".toRegex()
        val nestedSelection = selection.replace("[", "").replace("]", "")
        if (nestedSelection.matches(selectionRule)) {
            throw IllegalArgumentException(ErrorMessage.INPUT_NOT_CORRECT.getMessage())
        }
    }
}