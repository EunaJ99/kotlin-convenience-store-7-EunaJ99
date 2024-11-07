package controller

import store.*
import tools.Processer
import tools.Validator
import view.View

class Controller(private val view: View) {
    private val storage = Storage()
    private val promotionSystem = PromotionSystem()
    private val validator = Validator()
    private val processer = Processer()

    fun runBusiness() {
        val products = openShop()
        val counter = Counter(products.size)
        promotion(products, counter)
        calculate(products, counter)
    }

    // 초기 안내 및 재고 출력, 구매 리스트 수령 및 검증
    private fun openShop(): ArrayList<RequiredProduct> {
        var products: ArrayList<RequiredProduct>
        while (true) {
            try {
                products = getOrder()
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        return products
    }

    private fun getOrder(): ArrayList<RequiredProduct> {
        storage.openStorage()
        val ledger = storage.getLedger()
        val selection = view.welcomeCustomer(ledger)
        val products = understandInput(selection)
        val productFound = checkStocks(products)
        return productFound
    }

    private fun understandInput(selection: String): ArrayList<RequiredProduct> {
        val chopSelection = processer.chopInputWithComma(selection)
        chopSelection.forEach {
            validator.isSelectionFormatted(it)
        }
        val selectedProducts = processer.processRequirement(selection)
        return selectedProducts
    }

    private fun checkStocks(requirements: ArrayList<RequiredProduct>): ArrayList<RequiredProduct> {
        val foundRequirements = storage.findMultipleProducts(requirements)
        validator.isProductExists(foundRequirements)
        for (i in 0..requirements.lastIndex) {
            validator.isQuantityEnough(storage.findQuantity(foundRequirements[i].productNumber), requirements[i].quantity)
        }
        return foundRequirements
    }

    // 프로모션 적용
    private fun promotion(request: ArrayList<RequiredProduct>, counter: Counter) {
        promotionSystem.setPromotions()
        for (i in 0..request.lastIndex) {
            request[i].quantity += giveawayOffer(request[i])
            val freesAndNots = promoCoverage(request[i])
            counter.promotionEnroll(request[i].quantity, freesAndNots.first, freesAndNots.second, i)
        }
    }

    private fun giveawayOffer(request: RequiredProduct): Int {
        val ledger = storage.getLedger()
        val toRequire = promotionSystem.giveawayRequest(ledger[request.productNumber].promotion, request.quantity, ledger[request.productNumber].quantity)
        if (toRequire == 0 || !acceptOrNot(ledger[request.productNumber].name, toRequire)) {
            return 0
        }
        return toRequire
    }

    private fun acceptOrNot(name: String, toRequire: Int): Boolean {
        var answer: Boolean
        while (true) {
            try {
                answer = askOffer(name, toRequire)
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        return answer
    }

    private fun askOffer(name: String, toRequire: Int): Boolean {
        val answer = view.giveawayOffer(name, toRequire)
        validator.answerCheck(answer)
        return answer == "Y" || answer == "y"
    }

    private fun promoCoverage(required: RequiredProduct): Pair<Int, Int> {
        val product = storage.getLedger()[required.productNumber]
        val freesAndNots = promotionSystem.checkPromotionCoverage(product.promotion, required.quantity, product.quantity)
        if (freesAndNots.second != 0 && !regularOrNot(required.name, freesAndNots.second)) {
            return Pair(freesAndNots.first, -freesAndNots.second)
        }
        return freesAndNots
    }

    private fun regularOrNot(name: String, excess: Int): Boolean {
        var answer: Boolean
        while (true) {
            try {
                answer = askNotFree(name, excess)
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        return answer
    }

    private fun askNotFree(name: String, excess: Int): Boolean {
        val answer = view.notFreeNotice(name, excess)
        validator.answerCheck(answer)
        return answer == "Y" || answer == "y"
    }

    // 영수증에 들어갈 정보 취합 및 계산
    private fun calculate(request: ArrayList<RequiredProduct>, counter: Counter) {
        for (i in 0..request.lastIndex) {
            priceSet(request[i], i, counter)
        }
        val receiptInfo = counter.totalNumbers(request)
        receiptInfo.membership = membership(receiptInfo.totalPrice, counter)
        receiptInfo.totalPrice -= receiptInfo.membership
        view.receipt(receiptInfo)
    }

    private fun priceSet(required: RequiredProduct, index: Int, counter: Counter) {
        val product = storage.getLedger()[required.productNumber]
        val price = product.price.replace(",", "")
        counter.setPrice(index, price.toInt())
    }

    // 멤버십 할인 적용
    private fun membership(total: Int, counter: Counter): Int {
        var discount = 0
        if (membershipOrNot()) {
            discount = counter.membershipDiscount(total)
        }
        return discount
    }

    private fun membershipOrNot(): Boolean {
        var answer: Boolean
        while (true) {
            try {
                answer = askMembership()
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        return answer
    }

    private fun askMembership(): Boolean {
        val answer = view.membershipDiscount()
        validator.answerCheck(answer)
        return answer == "Y" || answer == "y"
    }
}