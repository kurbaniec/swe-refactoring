package com.gildedrose

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */

fun Item.improveQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality += value
    limitQuantity(minQuality, maxQuality)
}

fun Item.detoriateQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality -= value
    limitQuantity(minQuality, maxQuality)
}
fun Item.setQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality = value
    limitQuantity(minQuality, maxQuality)
}

fun Item.limitQuantity(minimumValue: Int?, maximumValue: Int?) {
    when {
        minimumValue != null && maximumValue != null -> limitQuantityIn(minimumValue, maximumValue)
        minimumValue != null && maximumValue == null -> limitQuantityAtLeast(minimumValue)
        minimumValue == null && maximumValue != null -> limitQuantityAtLeast(maximumValue)
    }
}

fun Item.limitQuantityIn(minimumValue: Int, maximumValue: Int) {
    quality = quality.coerceIn(minimumValue, maximumValue)
}

fun Item.limitQuantityAtLeast(minimumValue: Int) {
    quality = quality.coerceAtLeast(minimumValue)
}

fun Item.limitQuantityAtMost(maximumValue: Int) {
    quality = quality.coerceAtMost(maximumValue)
}