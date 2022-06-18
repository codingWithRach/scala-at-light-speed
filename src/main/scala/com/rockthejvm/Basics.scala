package com.rockthejvm

object Basics extends App {
  val meaningOfLife: Int = 42

  val aBoolean = false // type is optional

  val aString = "I love Scala"
  val aComposedString = "I" + " " + "love" + " " + "Scala"
  val anInterpolatedString = s"The meaning of life is $meaningOfLife"

  val expression = 2 + 3

  val ifExpression = if (meaningOfLife > 43) 56 else 999
  val chainedIfExpression = {
    if (meaningOfLife > 43) 56
    else if (meaningOfLife < 0) -2
    else if (meaningOfLife > 999) 78
    else 0
  }

  val aCodeBlock = {
    val aLocalValue = 67
    // other expressions
    aLocalValue + 3
  }

  def myFunction(x: Int, y: String): String = y + " " + x

  // functions can depend on their own definition, so are usually
  // recursive in practice
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else n * factorial(n-1)

  // in scala we don't use loops or iteration: we use recursion!
  // variables and loops are heavily discouraged

  // unit is the type of SIDE EFFECTS
  def myUnitReturningFunction(): Unit = {
    println("I don't love returning Unit")
  }
  val theUnit: Unit = {}
}
