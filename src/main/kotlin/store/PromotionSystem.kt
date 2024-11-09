package store

import camp.nextstep.edu.missionutils.DateTimes
import tools.Processer
import tools.Reader
import java.time.LocalDateTime

class PromotionSystem {
    private val promotionLedger = ArrayList<PromotionInfo>()

    fun setPromotions() {
        val rawPromotions = Reader().readPromotions()
        splitPromotionInfo(rawPromotions)
    }

    private fun splitPromotionInfo(rawPromotions: ArrayList<String>) {
        for (i in 1..rawPromotions.lastIndex) {
            val chopped = Processer().chopWithComma(rawPromotions[i])
            promotionLedger.add(PromotionInfo(chopped[0], chopped[1].toInt(), chopped[2].toInt(), chopped[3], chopped[4]))
        }
        promotionLedger.add(PromotionInfo("", 0, 0, "2000-01-01", "2000-01-02"))
    }

    fun giveawayRequest(name: String, required: Int, stock: Int): Int {
        val index = getPromotionInfo(name)
        if (index < 0) {
            return 0
        }
        val promotionInfo = promotionLedger[index]
        val promoUnit = promotionInfo.get + promotionInfo.buy
        if (promoUnit > 1 && required % promoUnit == promotionInfo.buy && stock >= required + promotionInfo.get) {
            return promotionInfo.get
        }
        return 0
    }

    private fun fullPromotionStock(promotionInfo: PromotionInfo, required: Int, stock: Int): Boolean {
        val full = required % getPromotionUnit(promotionInfo.name) == promotionInfo.buy
                && stock < required + promotionInfo.get
        return full
    }

    fun checkPromotionCoverage(name: String, required: Int, stock: Int): Pair<Int, Int> {
        val promoUnit = getPromotionUnit(name)
        if (promoUnit < 2) {
            return Pair(0, 0)
        }
        val fullStock = fullPromotionStock(promotionLedger[findPromotion(name)], required, stock)
        if (required / promoUnit > stock / promoUnit || fullStock) {
            val excess = required - (stock / promoUnit) * promoUnit
            return Pair(stock / promoUnit, excess)
        }
        return Pair(required / promoUnit, 0)
    }

    private fun getPromotionUnit(name: String): Int {
        val promotionInfo = promotionLedger[getPromotionInfo(name)]
        val promoUnit = promotionInfo.get + promotionInfo.buy
        return promoUnit
    }

    private fun getPromotionInfo(name: String): Int {
        val index = findPromotion(name)
        if (index >= 0 && isPromotionOngoing(index)) {
            return index
        }
        return promotionLedger.lastIndex
    }

    private fun isPromotionOngoing(index: Int): Boolean {
        val startDate = LocalDateTime.parse(promotionLedger[index].startDate + "T00:00:00")
        val endDate = LocalDateTime.parse(promotionLedger[index].endDate + "T23:59:59")
        val now = DateTimes.now()
        return startDate.isBefore(now) && endDate.isAfter(now)
    }

    private fun findPromotion(name: String): Int {
        var index = -1
        for (i in 0..promotionLedger.lastIndex) {
            if (promotionLedger[i].name == name) {
                index = i
                return index
            }
        }
        return index
    }
}