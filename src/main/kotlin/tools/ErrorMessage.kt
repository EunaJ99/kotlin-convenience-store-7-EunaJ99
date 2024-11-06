package tools

enum class ErrorMessage(private val message: String) {
    INPUT_NOT_CORRECT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INPUT_SOMETHING_WRONG("잘못된 입력입니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PRODUCT_NOT_ENOUGH("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    fun getMessage(): String {
        return "[ERROR] $message"
    }
}