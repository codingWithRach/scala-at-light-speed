package com.rockthejvm

object PatternMatching extends App {
  // switch expression
  val anInteger = 55
  val order = anInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anInteger + "th"
  }
  println(order)
  // pattern match is an expression, so can be reduced to a value

  // however, pattern matching takes things to a whole other level
  // because it's also able to deconstruct data structures into their constituent parts
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 43)
  val personGreeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "Something else"
  }
  println(personGreeting)
  // one of the benefits of case classes is being able to deconstruct them in pattern matching
  // it's also available for normal classes, but ONLY if you do a lot of magic behind the scenes

  // pattern matching can also deconstruct some other data structures
  // e.g. tuples
  val aTuple = ("Bon Jovi", "Rock")
  val bandDescription = aTuple match {
    case (band, genre) => s"$band belongs to the genre $genre"
    case _ => "I don't know what you're talking about"
  }

  // can also deconstruct more complex data structures
  // e.g. decomposing lists
  val aList = List(1, 2, 3)
  val listDescription = aList match {
    case List(_, 2, _) => "List containing 2 on its second position"
    case _ => "unknown list"
  }

  // if pattern match doesn't match anything, it will throw a MatchError
  // that's why for best practice we always include a case _

  // pattern matching will try all cases in sequence
  // i.e. if put case _ first, that's the one that it will process

  // pattern matching is much more powerful that we've shown here
  // but to quote Tim Ferris and his minimum effective dose: this is everything you need
  // to be able to work with 90% of all pattern matching use cases that you will ever see
  // in code bases

}
