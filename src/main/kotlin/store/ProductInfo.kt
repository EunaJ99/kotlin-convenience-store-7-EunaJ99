package store

data class ProductInfo(
    val name: String,
    val price: String,
    var quantity: Int,
    val promotion: String
)
