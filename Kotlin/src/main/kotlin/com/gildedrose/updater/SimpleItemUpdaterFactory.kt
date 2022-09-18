package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.updater.strategies.BaseItemUpdater

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
                contains("") -> BaseItemUpdater
                else -> BaseItemUpdater
            }
        }
    }

}