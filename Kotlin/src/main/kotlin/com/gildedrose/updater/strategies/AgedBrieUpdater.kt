package com.gildedrose.updater.strategies

import com.gildedrose.Item
import com.gildedrose.updater.ItemUpdater

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2022-09-18
 */
object AgedBrieUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        ++item.quality

    }
}