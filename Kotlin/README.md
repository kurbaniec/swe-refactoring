### Kacper Urbaniec | SWE | 21.09.2022

# Assignment 1: Refactoring

## 🔬 Test 

```bash
./gradlew test
```

## 📚 Strategy & Principles

*In the provided field within moodle explain the strategy and principles you followed to refactor the codebase and why you think that those changes caused an improvement. Try to confirm this hypotheses with one of the learned metrics.*

For this Coding Kata I wanted to try to follow Jeff Bay's *Object Calisthenics* for the refactoring process. I find most of the rules quite well thought and helpful but *spoiler alert*  I could not comply with all of them.  More about this later.

I chose Kotlin for this Kata as I am quite familiar with the language and like some of its functional aspects compared to traditional Java. Before starting the refactoring process, I wrote some basic unit tests to check what worked and what didn't (e.g. conjured items) with the old code. The tests helped fine-tune the requirements and check that everything works as it used to (if it was intended to).

The base idea for the refactoring was to provide a way to get an object implementing an interface called `ItemUpdater` that can be used to update an `Item` instance according to the business rule. 

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

The simple factory is not really a pattern. A factory with a factory method and concrete creator is a pattern but in my opinion it is not required in this simple example and the simple factory class can easily refactored in the future (into a factory method or even abstract factory). The main idea behind the simple factory was to put code that might change in the future (new `ItemUpdater` is needed) in one place.  A side note, the name `create` can be seen as a little misleading as `ItemUpdater`s are returned in the form of singletons and not actually created, but that was just my my take on optimising the code a little.  

The `getItemUpdater` kind of breaks some rules of the *Object Calisthenics*. The `when` expression does not exist in Java but is most reminiscent of a `switch` statement. So one needs to use `else` keyword to provide a base case and not the `default` keyword. It is not the same `else` as found in `if/else` and *Object Calisthenics* was (probably?) written before Kotlin was a thing so take this with a grain of salt. But in theory one could rewrite this in Java with a lot of `if` statements and early returns to follow the rules.

Now the `SimpleItemUpdaterFactory` can be added to the main class. With some basic restructuring, the class is now easy to navigate and follow.

```kotlin
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
```

When looking at the rules of `Object Calisthenics`, one can deduce that the `Item` class is breaking some rules. It features more than two instance variables that represent getter/setter properties. The class is also marked explicitly `open` to allow inheritance which is not typical for Kotlin. I thought about using inheritance to solve the Coding Kata, but it would not solve the getter/setter properties problem as these fields are public and cannot be magically removed in a subclass (Liskov Substitution Principle).  Furthermore, in my opinion, idiomatic Kotlin tries to reduce objected oriented aspects a bit and not use inheritance for everything (that is why classes are on default `final`). I could have also wrapped the `Item` in a custom `MyItem` class that uses the `Item` as its state. This would however require to change the `items` property in `GildedRose` or add an additional property that maps `Item`s to `MyItem`s.  It could be an viable alternative solution on its own, but in my opinion, the current simplefactory-itemupdate architecture would not really benefit from it. 

However, I quite liked the "Tell, don't ask" mindset from the "No getters/setters/properties" rule. An object should provide convenient methods so one can interact with the object easily without generating new errors because of misuse of the object. I often had code that looked like the following which broke the  "No getters/setters/properties" & "One dot per line" rule.

```kotlin
override fun updateQuality(item: Item): ItemUpdater {
	++item.quality
	item.quality = item.quality.coerceIn(MIN_QUALITY, MAX_QUALITY)
	return this
}
```

The method increments the quality and corrects it if its outside the bounds from the business rules. What if we can streamline this, let the object provide a method for this.

The requirements state that the `Item` class should not be changed. Fortunately, Kotlin has a neat feature called "Extension Functions" that allows us to define new methods outside the class definition. One can use them like normal methods, but they are actually resolved as static functions at runtime. Therefore, one can only use public properties/methods of the object in an extension function. In the case of the class `Item` this is not a problem.

```kotlin
fun Item.improveQuality(value: Int, minQuality: Int? = null, maxQuality: Int? = null) {
    quality += value
    limitQuality(minQuality, maxQuality) 
}

private fun Item.limitQuality(minimumValue: Int?, maximumValue: Int?) {
    when {
        minimumValue != null && maximumValue != null -> limitQualityIn(minimumValue, maximumValue)
        minimumValue != null && maximumValue == null -> limitQualityAtLeast(minimumValue)
        minimumValue == null && maximumValue != null -> limitQualityAtMost(maximumValue)
    }
}

fun Item.limitQualityIn(minimumValue: Int, maximumValue: Int) {
    quality = quality.coerceIn(minimumValue, maximumValue)
}
```

Through these extension function the earlier example can be simplified as follows.  Its much simpler and easier to read.

```kotlin
override fun updateQuality(item: Item): ItemUpdater {
	item.improveQuality(1, MIN_QUALITY, MAX_QUALITY)
	return this
}
```



mulitple dots per line

```
object BackstagePassesUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
        when {
            item.sellIn < 0 -> item.setQuality(MIN_QUALITY)
            item.sellIn < 5 -> item.improveQuality(3, MIN_QUALITY, MAX_QUALITY)
            item.sellIn < 10 -> item.improveQuality(2, MIN_QUALITY, MAX_QUALITY)
            item.sellIn >= 10 -> item.improveQuality(1, MIN_QUALITY, MAX_QUALITY)
        }
        return this
    }
}
```

or intendation

```
object BackstagePassesUpdater : ItemUpdater {
    override fun updateQuality(item: Item): ItemUpdater {
    	with(item) {
            when {
                sellIn < 0 -> setQuality(MIN_QUALITY)
                sellIn < 5 -> improveQuality(3, MIN_QUALITY, MAX_QUALITY)
                sellIn < 10 -> improveQuality(2, MIN_QUALITY, MAX_QUALITY)
                sellIn >= 10 -> improveQuality(1, MIN_QUALITY, MAX_QUALITY)
            }
        }
        return this
    }
}
```

