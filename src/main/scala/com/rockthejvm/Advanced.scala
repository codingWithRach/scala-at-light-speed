package com.rockthejvm

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {
  // lazy evaluation i.e. an expression is not evaluated until it's first used
  lazy val aLazyValue = 2

  // can be illustrated with a side effect
  lazy val lazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  }
  // if remove "lazy" and run the app, "I am so very lazy!" is printed to the console
  // otherwise, nothing is seen

  // lazy value is evaluated when first used
  val eagerValue = lazyValueWithSideEffect + 1
  // because I'm using lazyValueWithSideEffect for the first time, the code
  // block will be executed, and so "I am so very lazy!" will be printed to the console

  // useful in infinite collections, and some more rare use cases

  // ***************

  // "pseudo-collections" (although they're actually their own type)
  // Option and Try
  // useful in large code bases when you have unsafe methods
  def methodWhichCanReturnNull(): String = "hello, Scala"
  // other languages
  if (methodWhichCanReturnNull() == null) {
    // defensive code against null
  }
  // Scala equivalent
  val anOption = Option(methodWhichCanReturnNull())
  // option is a "collection" of at most one value:
  // if the method returns a valid value, the "collection" contains the value inside i.e. Some("hello, Scala")
  // if the method returns an invalid value (null), the "collection" contains None
  // you can now use pattern matching
  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }
  // don't need any null checks that you would normally add in defensive code
  // just wrap unsafe code in Option, and then use pattern matching
  // or can operate with Options like you would on collections with map, flatMap, filter

  // Try guards against methods that can throw exceptions
  def methodWhichCanThrowException(): String = throw new RuntimeException
  // other languages
  try {
    methodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }
  // adding many layers of this will lead to very defensive code adding complexity to
  // large codebases, and making code almost unreadable

  // Scala equivalent
  // a Try object, containing a String if the method worked correctly, or the Exception that was thrown
  val aTry = Try(methodWhichCanThrowException())
  // a Try is a "collection" with either a value (if code went well) or Exception (if didn't)
  val anotherStringProcessing = aTry match {
    case Success(validValue) => s"I have obtained a valid string: $validValue"
    case Failure(exception) => s"I have obtained an exception: $exception"
  }
  // use Try to avoid defensiveness with try/catch

  // the Try object and Option object can be processed like we did with collections
  // both have the map, flatMap and filter compositional functions (as well as some others)

  // *********************************

  // evaluating something on another thread i.e. asynchronous programming
  // this is done with another "pseudo-collection" known as a Future
  // I'm going to pass a code block, which will be evaluated on another thread
  val aFuture = Future({
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value")
    67
  })
  // to run a future we need to import an execution context at the top of the file
  // import scala.concurrent.ExecutionContext.Implicits.global
  // (we will discuss Implicits shortly)
  // the global value is equivalent of a thread pull i.e. a collection of threads on which
  // we can schedule the evaluation of this expression

  // if we run this app we will see "I am so very lazy!" and "Loading..."
  // but the main thread of the JVM will finish before this Future had the chance to evaluate
  // that's proof that it was evaluated on another thread

  // now add the following code to the main JVM thread
  Thread.sleep(2000)
  // now we get "I am so very lazy!", "Loading..." and "I have computed a value"

  // note the following alternative syntax
  val aFuture2 = Future {
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value")
    67
  }

  // a future is a "collection" which contains a value when the code block is evaluated
  // until then, it does not contain a value
  // however, a future is composable with map, flatMap and filter

  // the Future, Try and Option types are called monads in functional programming
  // they're a very touchy subject in functional programming because they are very very very
  // very abstract and hard to explain
  // for now, just think of them as "some sort of collection"

  // ******************************

  // implicits
  // one of the most powerful features of the Scala compiler because they allow for some
  // magic that you wouldn't have thought possible
  // there are two common use cases

  // 1. implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt: Int = 46
  println(aMethodWithImplicitArgs)
  // note that don't need to pass any arguments
  // compiler looks for something suitable it can inject

  // 2. implicit conversions
  // to add methods to existing types over which we don't have any control
  implicit class MyRichInteger(n: Int) {
    def isEven: Boolean = n % 2 == 0
  }
  println(23.isEven)
  // can do this even though isEven() does not belong to the Int class
  // if we remove the "implicit" modifier for the class, the code will turn red, as the method is not available for the Int type
  // but if I use the "implicit" modifier, the compiler is smart enough to find an implicit wrapper with an isEven method
  // the compiler effectively calls: new MyRichInteger(23).isEven

  // this makes Scala an incredibly expressive language, but is also dangerous
  // use implicits with care!

}
