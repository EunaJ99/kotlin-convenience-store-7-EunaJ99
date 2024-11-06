package tools

import store.RequiredProduct

class Processer {
    fun chopRequirementInput(input: String): List<String> = input.split(",")

    fun processRequirement(input: String): ArrayList<RequiredProduct> {
        val requirement = ArrayList<RequiredProduct>()
        val peeledInput = input.replace("[", "").replace("]", "")
        val choppedInput = peeledInput.split(",")
        for (i in 0..choppedInput.lastIndex) {
            requirement.add(splitSingleRequest(choppedInput[i]))
        }
        return requirement
    }

    private fun splitSingleRequest(input: String): RequiredProduct {
        val elements = input.split("-")
        val request = RequiredProduct(elements[0], elements[1].toInt())
        return request
    }
}