package com.gildedrose.updater.strategies

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.improveQuality
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.setQuality

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object BackstagePassesUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        when {
            item.sellIn < 0 -> item.setQuality(MIN_QUALITY)
            item.sellIn < 5 -> item.improveQuality(3, MIN_QUALITY, MAX_QUALITY)
            item.sellIn < 10 -> item.improveQuality(2, MIN_QUALITY, MAX_QUALITY)
            item.sellIn >= 10 -> item.improveQuality(1, MIN_QUALITY, MAX_QUALITY)
        }
        return this
    }
}