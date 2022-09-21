package com.gildedrose.updater

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.decreaseSellIn
import com.gildedrose.deteriorateQuality

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
interface ItemUpdater {
    fun updateSellIn(item: Item): ItemUpdater {
        item.decreaseSellIn(1)
        return this
    } 
    
    fun updateQuality(item: Item): ItemUpdater {
        var qualityDecrease = 1
        if (item.sellIn < 0)
            qualityDecrease *= 2
        item.deteriorateQuality(qualityDecrease, MIN_QUALITY, MAX_QUALITY)
        return this
    }
}