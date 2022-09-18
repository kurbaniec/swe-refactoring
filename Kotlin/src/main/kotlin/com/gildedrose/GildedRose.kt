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


//  fun updateQuality() {
//        service.update(items)
//        for (i in items.indices) {
//            if (items[i].name != "Aged Brie" && items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
//                if (items[i].quality > 0) {
//                    if (items[i].name != "Sulfuras, Hand of Ragnaros") {
//                        items[i].quality = items[i].quality - 1
//                    }
//                }
//            } else {
//                if (items[i].quality < 50) {
//                    items[i].quality = items[i].quality + 1
//
//                    if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
//                        if (items[i].sellIn < 11) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1
//                            }
//                        }
//
//                        if (items[i].sellIn < 6) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
//                items[i].sellIn = items[i].sellIn - 1
//            }
//
//            if (items[i].sellIn < 0) {
//                if (items[i].name != "Aged Brie") {
//                    if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
//                        if (items[i].quality > 0) {
//                            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
//                                items[i].quality = items[i].quality - 1
//                            }
//                        }
//                    } else {
//                        items[i].quality = items[i].quality - items[i].quality
//                    }
//                } else {
//                    if (items[i].quality < 50) {
//                        items[i].quality = items[i].quality + 1
//                    }
//                }
//            }
//        }
//    }

}

