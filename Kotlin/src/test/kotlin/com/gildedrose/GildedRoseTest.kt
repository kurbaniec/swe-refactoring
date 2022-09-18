package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GildedRoseTest {
    object Constants {
        const val MAX_QUALITY = 50
        const val MIN_QUALITY = 0
    }

    @Test
    fun `generic item`() {
        val item = listOf(Item("foo", 10, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(9, updatedItem.sellIn)
        assertEquals(9, updatedItem.quality)
    }

    @Test
    fun `generic item past sellIn`() {
        val item = listOf(Item("foo", 10, 10))
        val app = GildedRose(item.toTypedArray())
        for (i in 1..20) app.updateQuality()
        val updatedItem = app.items.first()
        assertTrue(updatedItem.sellIn < 0)
        assertEquals(Constants.MIN_QUALITY, updatedItem.quality)
    }

    @Test
    fun `generic item past sellIn degradation`() {
        val item = listOf(Item("foo", 0, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertTrue(updatedItem.sellIn < 0)
        assertEquals(8, updatedItem.quality)
    }

    @Test
    fun `generic item wrong initial quality`() {
        val item = listOf(Item("foo", 10, 100))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(Constants.MAX_QUALITY, updatedItem.quality)
    }

    @Test
    fun `aged brie`() {
        val item = listOf(Item("Aged Brie", 10, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(9, updatedItem.sellIn)
        assertEquals(11, updatedItem.quality)
    }

    @Test
    fun `sulfuras`() {
        val item = listOf(Item("Sulfuras, Hand of Ragnaros", 10, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(10, updatedItem.sellIn)
        assertEquals(10, updatedItem.quality)
    }

    @Test
    fun `sulfuras with higher quality`() {
        val item = listOf(Item("Sulfuras, Hand of Ragnaros", 10, 80))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(10, updatedItem.sellIn)
        assertEquals(80, updatedItem.quality)
    }

    @Test
    fun `backstage passes`() {
        val item = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 11, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(10, updatedItem.sellIn)
        assertEquals(11, updatedItem.quality)
    }

    @Test
    fun `backstage passes quality model`() {
        val item = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 11, 10))
        val app = GildedRose(item.toTypedArray())
        val expectedSellIn = (10 downTo -1).toList()
        val expectedQuality = listOf(11, 13, 15, 17, 19, 21, 24, 27, 30, 33, 36, 0)
        val expected = expectedSellIn.zip(expectedQuality)
        for ((sellIn, quality) in expected) {
            app.updateQuality()
            val updatedItem = app.items.first()
            assertEquals(sellIn, updatedItem.sellIn)
            assertEquals(quality, updatedItem.quality)
        }
    }

    @Test
    fun `conjured`() {
        val item = listOf(Item("Conjured Mana Cake", 10, 10))
        val app = GildedRose(item.toTypedArray())
        app.updateQuality()
        val updatedItem = app.items.first()
        assertEquals(9, updatedItem.sellIn)
        assertEquals(8, updatedItem.quality)
    }

}


