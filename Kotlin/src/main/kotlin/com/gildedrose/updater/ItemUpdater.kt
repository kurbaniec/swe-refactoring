package com.gildedrose.updater

import com.gildedrose.GildedRose
import com.gildedrose.GildedRose.Constants.MAX_QUALITY
import com.gildedrose.GildedRose.Constants.MIN_QUALITY
import com.gildedrose.Item

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
        item.quality = item.quality
            .coerceIn(MIN_QUALITY, MAX_QUALITY)
        return this
    }
}