package tools

import store.RequiredProduct
import java.text.DecimalFormat

class Processer {
    fun chopWithComma(input: String): List<String> = input.split(",")

    fun processSelection(input: String): ArrayList<RequiredProduct> {
        val requirement = ArrayList<RequiredProduct>()
        val peeledInput = input.replace("[", "").replace("]", "")
        val choppedInput = peeledInput.split(",")
        for (i in 0..choppedInput.lastIndex) {
            requirement.add(findSelectionInfo(choppedInput[i]))
        }
        return duplicateCheck(requirement)
    }

    private fun findSelectionInfo(input: String): RequiredProduct {
        val elements = input.split("-")
        val request = RequiredProduct(elements[0], elements[1].toInt(), -1)
        return request
    }

    private fun duplicateCheck(requirements: ArrayList<RequiredProduct>): ArrayList<RequiredProduct> {
        val map: MutableMap<String, Int> = mutableMapOf()
        for (i in 0..requirements.lastIndex) {
            if (map.contains(requirements[i].name)) {
                requirements[map.getValue(requirements[i].name)].quantity += requirements[i].quantity
                requirements[i].quantity = 0
                continue
            }
            map[requirements[i].name] = i
        }
        return requirements
    }

    fun processPrice(price: String): String {
        val priceNumber = price.toInt()
        val priceFormat = DecimalFormat("#,###")
        return priceFormat.format(priceNumber)
    }
}