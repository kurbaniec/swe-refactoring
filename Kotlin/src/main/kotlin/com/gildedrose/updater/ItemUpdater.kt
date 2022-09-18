package com.gildedrose.updater

import com.gildedrose.GildedRose
import com.gildedrose.Item

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
interface ItemUpdater {
    fun updateSellIn(item: Item): ItemUpdater {

    } 
    
    fun updateQuality(item: Item): ItemUpdater {
        
    }
}