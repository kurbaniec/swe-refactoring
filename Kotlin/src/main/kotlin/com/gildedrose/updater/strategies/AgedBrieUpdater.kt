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
object AgedBrieUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        ++item.quality
        item.limitQuantityIn(MIN_QUALITY, MAX_QUALITY)
        return this
    }
}