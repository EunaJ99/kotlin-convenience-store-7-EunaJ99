package store

class Counter(productCount: Int) {
    private val freeCount = ArrayList<Int>()
    private val notFreeCount = ArrayList<Int>()

    init {
        for (i in 1..productCount) {
            freeCount.add(0)
            notFreeCount.add(0)
        }
    }

    fun promotionEnroll(totalRequest: Int, free: Int, correction: Int, productNumber: Int) {
        freeCount[productNumber] += free
        notFreeCount[productNumber] += (totalRequest - free)
        if (correction < 0) {
            notFreeCount[productNumber] += correction
        }
    }
}