package store

import tools.Processer

class Counter(productCount: Int) {
    private val freeCount = ArrayList<Int>()
    private val notFreeCount = ArrayList<Int>()
    private val prices = ArrayList<Int>()

    init {
        for (i in 1..productCount) {
            freeCount.add(0)
            notFreeCount.add(0)
            prices.add(0)
        }
    }

    fun promotionEnroll(totalRequest: Int, free: Int, correction: Int, productNumber: Int) {
        freeCount[productNumber] += free
        notFreeCount[productNumber] += (totalRequest - free)
        if (correction < 0) {
            notFreeCount[productNumber] += correction
        }
    }

    fun setPrice(index: Int, price: Int) {
        prices[index] += price
    }

    fun totalNumbers(request: ArrayList<RequiredProduct>): ReceiptInfo {
        var receiptInfo = ReceiptInfo()
        for (i in 0..request.lastIndex) {
            val singleProductInfo = totalProductInfo(request[i], i)
            receiptInfo = accumulateReceiptInfo(receiptInfo, singleProductInfo)
        }
        receiptInfo.priceToPay = receiptInfo.totalPrice - receiptInfo.totalDiscount
        return receiptInfo
    }

    private fun totalProductInfo(request: RequiredProduct, index: Int): ProductFinalInfo {
        val totalItems = freeCount[index] + notFreeCount[index]
        val totalPrice = totalItems * prices[index]
        val freeItems = freeCount[index]
        val discount = freeItems * prices[index]
        return ProductFinalInfo(request.name, totalItems, totalPrice, freeItems, discount)
    }

    private fun accumulateReceiptInfo(receiptInfo: ReceiptInfo, singleProductInfo: ProductFinalInfo): ReceiptInfo {
        receiptInfo.products.add(singleProductInfo)
        receiptInfo.totalQuantity += singleProductInfo.quantity
        receiptInfo.totalPrice += singleProductInfo.price
        receiptInfo.totalDiscount += singleProductInfo.discount
        return receiptInfo
    }

    fun membershipDiscount(total: Int): Int {
        var newTotal = (total * 0.2).toInt()
        if (newTotal > 8000) {
            newTotal = 8000
        }
        return newTotal
    }
}