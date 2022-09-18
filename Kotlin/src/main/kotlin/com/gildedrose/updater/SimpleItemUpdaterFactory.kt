package com.gildedrose.updater

import com.gildedrose.Item

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

        with(item.name) {
            getItemUpdaterForString()
        }
    }

    // Quite ugly
    private fun String.getItemUpdaterForString() {
        when {
            contains("sulfur") -> ...
        }
    }
}