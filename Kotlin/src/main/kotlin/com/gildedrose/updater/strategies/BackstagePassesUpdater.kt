package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.utils.limitQuantityIn

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object BackstagePassesUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        when {
            item.sellIn < 0 -> item.quality = MIN_QUALITY
            item.sellIn < 5 -> item.quality += 3
            item.sellIn < 10 -> item.quality += 2
            item.sellIn >= 10 -> ++item.quality
        }
        item.limitQuantityIn(MIN_QUALITY, MAX_QUALITY)
        return this
    }
}