package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.utils.limitQuantityAtLeast

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object SulfurasUpdater : ItemUpdater {
    override fun updateSellIn(item: Item): ItemUpdater {
//        if (item.sellIn != Int.MAX_VALUE)
//            item.sellIn = Int.MAX_VALUE
        return this
    }

    override fun updateQuality(item: Item): ItemUpdater {
        item.limitQuantityAtLeast(MIN_QUALITY)
        return this
    }
}