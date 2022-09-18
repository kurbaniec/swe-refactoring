package com.gildedrose.utils

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
fun Int.limit(minimumValue: Int, maximumValue: Int) {
    this = this.coerceIn(minimumValue, maximumValue)
}