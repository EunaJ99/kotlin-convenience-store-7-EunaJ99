package store

data class ReceiptInfo(
    val products: ArrayList<ProductFinalInfo> = ArrayList(),
    var totalQuantity: Int = 0,
    var totalPrice: Int = 0,
    var totalDiscount: Int = 0,
    var membership: Int = 0,
    var priceToPay: Int = 0
)
