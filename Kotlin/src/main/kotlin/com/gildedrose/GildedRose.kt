package com.gildedrose

import com.gildedrose.updater.ItemUpdater
import com.gildedrose.updater.SimpleItemUpdaterFactory

class GildedRose(var items: Array<Item>) {
    private val factory = SimpleItemUpdaterFactory()

    companion object Constants {
        const val MAX_QUALITY = 50
        const val MIN_QUALITY = 0
    }

    fun updateQuality() {
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

