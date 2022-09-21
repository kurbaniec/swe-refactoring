### Kacper Urbaniec | SWE | 21.09.2022

# Assignment 1: Refactoring

## 🔬 Test 

```bash
./gradlew test
```

## 📚 Strategy & Principles

*In the provided field within moodle explain the strategy and principles you followed to refactor the codebase and why you think that those changes caused an improvement. Try to confirm this hypotheses with one of the learned metrics.*

For this Coding Kata I wanted to try to follow Jeff Bay's *Object Calisthenics* for the refactoring process. I find most of the rules quite well thought and helpful but *spoiler alert*  I could not comply with all of them.  More about this later.

I chose Kotlin for this Kata as I am quite familiar with the language and like some of its functional aspects compared to traditional Java. The base idea for the refactoring was to provide a way to get an object implementing an interface called `ItemUpdater` that can be used to update an `Item` instance according to the business rule. 

```kotlin
interface ItemUpdater {
    fun updateSellIn(item: Item): ItemUpdater {
        item.decreaseSellIn(1)
        return this
    } 
    
    fun updateQuality(item: Item): ItemUpdater {
        var qualityDecrease = 1
        if (item.sellIn < 0)
            qualityDecrease *= 2
        item.deteriorateQuality(qualityDecrease, MIN_QUALITY, MAX_QUALITY)
        return this
    }
}
```

The interface contains default implementations that can be used to process an `Item` with basic requirements (decrease `sellIn` & `quality` by 1, decrease further when `sellIn` under 0). All concrete `ItemUpdater` have the single responsibility to update an `Item`. Also the methods can be chained and swapped in order as both return the updater object. Method chaining is frequently used in Kotlin and in my opinion an idiomatic approach. 

All concrete `ItemUpdater`s are implemented as singletons in order to prevent multiple instantiations during runtime. In Java this would require some amount of boilerplate code, in Kotlin this can be easily achieved with the `object ` keyword.

The default `ItemUpdater` that relies on the interface default implementation becomes practically a one liner.

```kotlin
object BaseItemUpdater : ItemUpdater
```

Other `ItemUpdater` like the one for "Aged Brie" just need to override the `updateQuality` method according to business rules.

```kotlin
object AgedBrieUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        item.improveQuality(1, MIN_QUALITY, MAX_QUALITY)
        return this
    }
}
```

But how can one acquire  the correct `ItemUpdater` corresponding to an `Item`. I implemented a simple factory that returns `ItemUpdater`s based on the `Item`'s name. 

```kotlin
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
```

The simple factory is not really a pattern. A factory with an factory method and concrete creator is a pattern but in my opinion it is not required in this simple example and the simple factory class can easily refactored in the future (into a factory method or even abstract factory). The main idea behind the simple factory was to put code that might change in the future (new `ItemUpdater` is needed) in one place.  A side note, the name `create` can be seen as a little misleading as `ItemUpdater`s are returned in the form of singletons and not actually created, but that was just my my take on optimising the code a little.  

