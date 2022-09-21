package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.limitQuantityIn

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object ConjuredUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        val currentQuality = item.quality
        super.updateQuality(item)
        val degradation = currentQuality - item.quality
        item.quality -= degradation
        item.limitQuantityIn(MIN_QUALITY, MAX_QUALITY)
        return this
    }


}