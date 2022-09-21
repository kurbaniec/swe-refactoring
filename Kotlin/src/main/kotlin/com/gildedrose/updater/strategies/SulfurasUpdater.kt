package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.limitQualityAtLeast

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object SulfurasUpdater : ItemUpdater {
    override fun updateSellIn(item: Item): ItemUpdater {
        return this
    }

    override fun updateQuality(item: Item): ItemUpdater {
        item.limitQualityAtLeast(MIN_QUALITY)
        return this
    }
}