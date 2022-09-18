package com.gildedrose.updater

import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item
import com.gildedrose.utils.limitQuantityIn

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
interface ItemUpdater {
    fun updateSellIn(item: Item): ItemUpdater {
        --item.sellIn
        return this
    } 
    
    fun updateQuality(item: Item): ItemUpdater {
        --item.quality
        if (item.sellIn < 0)
            --item.quality
        item.limitQuantityIn(MIN_QUALITY, MAX_QUALITY)
        return this
    }
}