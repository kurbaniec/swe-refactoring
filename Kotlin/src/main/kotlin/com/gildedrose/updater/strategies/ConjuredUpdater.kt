@file:Suppress("SameParameterValue")

package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.deteriorateQuality
import com.gildedrose.updater.ItemUpdater

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object ConjuredUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        multiplyDegradation(item, 2) {
            super.updateQuality(it)
        }
        return this
    }

    private fun multiplyDegradation(
        item: Item, factor: Int, degradationFn: (Item)->ItemUpdater
    ) {
        val currentQuality = item.quality
        degradationFn(item)
        val currentDegradation = currentQuality - item.quality
        val multipliedDegradation = currentDegradation * (factor-1)
        item.deteriorateQuality(multipliedDegradation, MIN_QUALITY, MAX_QUALITY)
    }
}