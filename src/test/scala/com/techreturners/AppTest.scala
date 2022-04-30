package com.techreturners

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AppTest extends AnyFlatSpec with Matchers{

  "A string from the app" should "be Hi from Tech Returners" in {
    App.someString should be ("Hi from Tech Returners")
  }
}
