package com.gildedrose.utils

import com.gildedrose.Item

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
fun Item.limitQuantityIn(minimumValue: Int, maximumValue: Int) {
    quality = quality.coerceIn(minimumValue, maximumValue)
}

fun Item.limitQuantityAtLeast(minimumValue: Int) {
    quality = quality.coerceAtLeast(minimumValue)
}

fun Item.limitQuantityAtMost(maximumValue: Int) {
    quality = quality.coerceAtMost(maximumValue)
}