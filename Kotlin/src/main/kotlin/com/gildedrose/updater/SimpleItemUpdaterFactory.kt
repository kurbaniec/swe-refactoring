package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.updater.strategies.*

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
class SimpleItemUpdaterFactory {
    fun create(item: Item): ItemUpdater {
        val simpleName = item.name
            .lowercase()

        return with(simpleName) {
            when {
                contains("aged brie") -> AgedBrieUpdater
                contains("sulfuras") -> SulfurasUpdater
                contains("backstage passes") -> BackstagePassesUpdater
                contains("conjured") -> ConjuredUpdater
                else -> BaseItemUpdater
            }
        }
    }
}