package com.gildedrose

import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater
import com.gildedrose.updater.SimpleItemUpdaterFactory

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
class GlidedRoseService {
    private val factory = SimpleItemUpdaterFactory()

    fun update(items: Array<Item>) {
        items.forEach { item ->
            update(item)
        }
    }

    private fun update(item: Item) {
        getItemUpdater(item)
            .updateSellIn(item)
            .updateQuality(item)
    }

    private fun getItemUpdater(item: Item): ItemUpdater {
        return factory.create(item)
    }
}