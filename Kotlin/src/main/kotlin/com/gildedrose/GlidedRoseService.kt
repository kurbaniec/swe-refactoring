package com.gildedrose

import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
class GlidedRoseService {

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

    }
}