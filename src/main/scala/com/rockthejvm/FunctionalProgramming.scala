package com.rockthejvm

object FunctionalProgramming extends App {
  // Recap
  // Scala is OO
  class Person(name: String) {
    def apply(age: Int): Unit = println(s"I have age $age years")
  }
  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) // invoking bob as a function

  // Scala runs on JVM = Java Virtual Machine
  // JVM was fundamentally built for Java
  // JVM knows what an object is, but doesn't know what a function is
  // In functional programming, we want to work with functions as first class elements of programming
  // i.e. we want to work with functions like we work with any other type of value
  // - compose functions
  // - pass functions as arguments
  // - return functions as results
  // these are the sorts of things we normally operate on when we work on objects or values

  // Scala people invented some clever stuff: FunctionX
  // the following is a function that takes an Int and returns an Int
  // this is a plain trait with an apply method
  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }
  // the above has defined an instance of the Function1 trait

  simpleIncrementer.apply(23) // 24
  simpleIncrementer(23) // equivalent

  // we've instantiated a trait, which can be invoked as a function
  // the only thing that it supports is to be invoked like a function
  // ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTIONX TYPES
  // FunctionX = Function1, Function2, ... Function22
  // 22 is the maximum number of arguments you can pass to a function
  // (the final argument in the definition is always the return value)
  // All Scala functions are instances of this FunctionX trait

  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("I love", "Scala")

  // syntax sugar 1: apply method
  val doubler: Function1[Int, Int] = (x: Int) => 2 * x
  doubler(4) // 8
  // equivalent of above:
  // override def apply(x: Int) = 2 * x

  // syntax sugar 2: function type
  val doubler2: Int => Int = (x: Int) => 2 * x
  // Int => Int is equivalent to Function1[Int, Int]
  // so doubler2: Function1[Int, Int] = new Function1[Int, Int] etc.

  // syntax sugar 3: omit the type altogether because the compiler is smart enough to infer it
  val doubler3 = (x: Int) => 2 * x

  // The goal of functional programming is to be able to compose functions,
  // pass functions as arguments, and return functions as results
  // Methods that take functions as arguments or return functions as results
  // are called higher-order functions
  val aMappedList = List(1, 2, 3).map(x => x + 1) // x is inferred as an Int
  // x => x + 1 is an anonymous function, passed as argument to the map method
  // the map method is a higher order function

  // the return value of the above is another list
  println(aMappedList)
  // the application of a method on a list (or on any object) that is due to
  // modify the original object will actually return another instance

  // another classic higher order function is flatMap
  val aFlatMappedList = List(1, 2, 3).flatMap(x => List(x, 2 * x))
  println(aFlatMappedList) // List(1, 2, 2, 4, 3, 6)
  // used very often in practice

  // note the following quirky alternative syntax(es)
  val aFlatMappedList2 = List(1, 2, 3).flatMap {
    x => List(x, 2 * x)
  }
  val aFlatMappedList3 = List(1, 2, 3).flatMap { x =>
    List(x, 2 * x)
  }

  // another classical higher order function is filter
  val aFilteredList = List(1, 2, 3, 4, 5).filter(x => x <= 3)
  // x => x <= 3 is an anonymous function
  // filter returns a new list that only contains the elements for which the anonymous function returns true
  println(aFlatMappedList2) // List(1, 2, 3)

  // syntax sugar
  val aFilteredList1 = List(1, 2, 3, 4, 5).filter( _ <= 3)
  // equivalent to x => x <= 3

  // every single call to map, flatMap or filter returns another instance of a List
  // so we can chain applications to map, flatMap or filter
  // e.g. create all pairs between numbers 1, 2, 3 and letters a, b, c
  val allPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  // List("1-a", "1-b", ... "3-c")

  // in big Scala codebases, chains such as this become increasingly hard to read
  // if the logic is increasingly complex
  // human readable form: for comprehensions
  val alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"
  // equivalent to allPairs above

  // Collections: Lists are fundamental collection of functional programming
  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head // 1
  val rest = aList.tail // List(2, 3, 4, 5)

  // lists can be appended and prepended with elements
  val aPrependedList = 0 :: aList // List(0, 1, 2, 3, 4, 5)
  val anExtendedList = 0 +: aList :+ 6 // List(0, 1, 2, 3, 4, 5, 6)

  // sequences
  val aSequence: Seq[Int] = Seq(1, 2, 3) // Seq.apply(1, 2, 3)
  // main characteristic is that can access an element at a given index
  val accessedElement = aSequence(1) // 2

  // vector: a particular type of sequence that is very fast for large data
  val aVector = Vector(1, 2, 3, 4, 5)

  // sets: collections with no duplicates (the order is not important)
  val aSet = Set(1, 2, 3, 4, 1, 2, 3) // Set(1, 2, 3, 4)
  // main property and fundamental method is to check whether an element is contained within the set
  val setHas5 = aSet.contains(5) // false
  val anAddedSet = aSet + 5 // Set(1, 2, 3, 4, 5)
  val aRemovedSet = aSet - 3 // Set(1, 2, 4)

  // ranges - useful for "iteration" although obviously we use map an flatMap to work on the ranges
  val aRange = 1 to 1000
  // fictitious collection that does not contain all the elements from 1 to 1000, but acts as if it did
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2, 4, 6, ... 2000)

  // tuples i.e. groups of values under the same value
  val aTuple = ("Bon Jovi", "Rock", 1982)

  // maps i.e. dictionaries
  val aPhonebook: Map[String, Int] = Map(
    ("Daniel", 5542221),
    "Jane" -> 45765555 // alternative syntax
  )

}
