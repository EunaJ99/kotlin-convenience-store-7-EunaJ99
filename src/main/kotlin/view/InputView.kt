package view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readItem(): String {
        println(SELECT_ITEM)
        val input = Console.readLine()
        return input
    }

    fun readPromotionFill(name: String, request: Int): String {
        println()
        println(PROMOTION_NEED.format(name, request))
        val input = Console.readLine()
        return input
    }

    fun readIsRegular(name: String, excess: Int): String {
        println()
        println(PROMOTION_UNAVAILABLE.format(name, excess))
        val input = Console.readLine()
        return input
    }

    fun readIsMembership(): String {
        println()
        println(MEMBERSHIP_DISCOUNT)
        val input = Console.readLine()
        return input
    }

    fun readIsContinue(): String {
        println()
        println(START_ALL_OVER)
        val input = Console.readLine()
        return input
    }

    companion object {
        const val SELECT_ITEM = "구매하실 상품명과 수량을 입력해 주세요."
        const val PROMOTION_NEED = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
        const val PROMOTION_UNAVAILABLE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
        const val MEMBERSHIP_DISCOUNT = "멤버십 할인을 받으시겠습니까?"
        const val START_ALL_OVER = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"
    }
}