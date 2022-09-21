package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.lowercaseName
import com.gildedrose.updater.strategies.*

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
class SimpleItemUpdaterFactory {
    fun create(item: Item): ItemUpdater {
        val lowercaseName = item.lowercaseName()
        return getItemUpdater(lowercaseName)
    }

    private fun getItemUpdater(lowercaseName: String): ItemUpdater {
        return when {
            lowercaseName.contains("aged brie") -> AgedBrieUpdater
            lowercaseName.contains("sulfuras") -> SulfurasUpdater
            lowercaseName.contains("backstage passes") -> BackstagePassesUpdater
            lowercaseName.contains("conjured") -> ConjuredUpdater
            else -> BaseItemUpdater
        }
    }
}