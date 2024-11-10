package store

class Counter(productCount: Int) {
    private val freeProducts = ArrayList<Int>()
    private val regularProducts = ArrayList<Int>()
    private val prices = ArrayList<Int>()

    init {
        for (i in 1..productCount) {
            freeProducts.add(0)
            regularProducts.add(0)
            prices.add(0)
        }
    }

    fun enrollProducts(totalRequest: Int, free: Int, correction: Int, productNumber: Int) {
        freeProducts[productNumber] += free
        regularProducts[productNumber] += (totalRequest - free)
        if (correction < 0) {
            regularProducts[productNumber] += correction
        }
    }

    fun setPrice(index: Int, price: Int) {
        prices[index] += price
    }

    fun writeReceipt(request: ArrayList<RequiredProduct>): ReceiptInfo {
        var receiptInfo = ReceiptInfo()
        for (i in 0..request.lastIndex) {
            val productReceipt = productValues(request[i], i)
            receiptInfo = mergeProductInfo(receiptInfo, productReceipt)
        }
        receiptInfo.priceToPay = receiptInfo.totalPrice - receiptInfo.totalDiscount
        return receiptInfo
    }

    private fun productValues(request: RequiredProduct, index: Int): ProductFinalInfo {
        val totalCount = freeProducts[index] + regularProducts[index]
        val totalPrice = totalCount * prices[index]
        val freeCount = freeProducts[index]
        val discount = freeCount * prices[index]
        return ProductFinalInfo(request.name, totalCount, totalPrice, freeCount, discount)
    }

    private fun mergeProductInfo(receiptInfo: ReceiptInfo, singleProductInfo: ProductFinalInfo): ReceiptInfo {
        receiptInfo.products.add(singleProductInfo)
        receiptInfo.totalQuantity += singleProductInfo.quantity
        receiptInfo.totalPrice += singleProductInfo.price
        receiptInfo.totalDiscount += singleProductInfo.discount
        return receiptInfo
    }

    fun membershipDiscount(receiptInfo: ReceiptInfo): ReceiptInfo {
        var discount = (receiptInfo.totalPrice * 0.2).toInt()
        if (discount > 8000) {
            discount = 8000
        }
        receiptInfo.membership = discount
        receiptInfo.totalDiscount -= discount
        return receiptInfo
    }
}