package com.rockthejvm

object ObjectOrientation extends App {
  // because ObjectOrientation extends App, it's as if you already had:
  // java equivalent: public static void main(String[] args)
  // the main method simply executes the object's body

  class Animal {
    val age: Int = 0
    def eat(): Unit = println("I'm eating")
  }
  val anAnimal = new Animal

  class Dog(val name: String) extends Animal
  val aDog = new Dog("Lassie")
  aDog.name

  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat() // the most derived method will be called at runtime

  // abstract class
  abstract class WalkingAnimal {
    val hasLegs = true // by default public: can restrict by using private or protected
    // private - only the class has access to the member or method
    // protected - only the class and its descendants have access to the member or method
    def walk(): Unit
  }

  // interface = ultimate abstract type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }
  trait Philosopher {
    def ?!(thought: String): Unit // ?! is a valid method name
  }


  // single class inheritance and multi-trait "mixing"
  class Crocodile extends Animal with Carnivore with Philosopher {
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")
    override def ?!(thought:  String): Unit = println(s"I was thinking: $thought")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // object method argument: infix notation
  // infix notation is only available for methods with ONE argument
  aCroc ?! "What if we could fly?"
  // ?! resembles an operator: in Scala, operators are actually methods
  // the following are equivalent
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2)

  // anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur, so I can eat pretty much anything")
  }

  // singleton object
  object MySingleton { // the only instance of the MySingleton type
    val mySpecialValue = 53278
    def mySpecialMethod(): Int = 5327
    def apply(x: Int): Int = x + 1
  }
  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // both lines equivalent

  object Animal {  // class Animal and object Animal are companions (can also be applied to traits)
    // companions can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    // normally never use singleton Animal as an instance while other Animals are present
    // normally use Animal companion object to access things that do not depend on instances of the animal class
    val canLiveIndefinitely = false
  }
  val animalsCanLiveForever = Animal.canLiveIndefinitely // "static" fields/methods

  // case classes = lightweight data structures with some boilerplate
  // compiler automatically generates the following:
  // - sensible equals and hash code
  // - sensible and quick serialisation
  // - companion object with apply method (which is why new keyword isn't needed)
  // - pattern matching (see future chapter)
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 54) // equivalent to Person.apply("Bob", 54)

  // exceptions (special objects)
  try {
    // code that can throw
    val x: String = null
    x.length
  } catch {
    case e: Exception => "some faulty error message"
  } finally {
    // execute some code no matter what e.g. closing connections, files etc.
  }

  // generics (works for classes and traits)
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  // using a generic with a concrete type
  val aList: List[Int] = List(1, 2, 3) // List companion object applied to elements 1, 2, 3 i.e. List.apply(1, 2, 3)
  val first = aList.head // compiler knows first is of type Int
  val rest = aList.tail // compiler knows rest is of type List[Int]

  // same List functionality can be applied to other types
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head // string
  val restHead = aStringList.tail // List[String]

  // In Scala operate with IMMUTABLE values
  // i.e. any operation to an instance of a class should result in another instance
  // don't mutate or change the values inside an object - always return another one
  // 1. works miracles in multithreaded/distributed environments and speed up development
  // 2. helps make sense of the code ("reasoning about")
  val reversedList = aList.reverse // returns a NEW list

  // Scala is closest to the OO ideal
  // marketed as a mix between OO and functional programming
  // all the code and all the values that we operate with are inside an instance of some type
}
