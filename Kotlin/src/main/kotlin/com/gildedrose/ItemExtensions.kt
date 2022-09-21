package com.gildedrose

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */

fun Item.lowercaseName(): String {
    return name.lowercase()
}

fun Item.increaseSellIn(value: Int) {
    sellIn += value
}

fun Item.decreaseSellIn(value: Int) {
    sellIn -= value
}

fun Item.setSellIn(value: Int) {
    sellIn = value
}

fun Item.improveQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality += value
    limitQuality(minQuality, maxQuality)
}

fun Item.deteriorateQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality -= value
    limitQuality(minQuality, maxQuality)
}
fun Item.setQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality = value
    limitQuality(minQuality, maxQuality)
}

private fun Item.limitQuality(minimumValue: Int?, maximumValue: Int?) {
    when {
        minimumValue != null && maximumValue != null -> limitQualityIn(minimumValue, maximumValue)
        minimumValue != null && maximumValue == null -> limitQualityAtLeast(minimumValue)
        minimumValue == null && maximumValue != null -> limitQualityAtMost(maximumValue)
    }
}

fun Item.limitQualityIn(minimumValue: Int, maximumValue: Int) {
    quality = quality.coerceIn(minimumValue, maximumValue)
}

fun Item.limitQualityAtLeast(minimumValue: Int) {
    quality = quality.coerceAtLeast(minimumValue)
}

fun Item.limitQualityAtMost(maximumValue: Int) {
    quality = quality.coerceAtMost(maximumValue)
}