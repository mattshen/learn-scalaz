package example

import example.Monoid.{Factor, multiMonoid}
import example.MonoidOp._
import example.Sums._

object Hello extends App {

  println("===============================================")
  println("sum of a String List: ")
  println(sum(List("a", "b", "c")))

  println("sum of a Int List: ")
  println(sum(List(1,2,3,4)))

  println("product of Int @@ Factor List: ")
  println(sum(List(Factor(1), Factor(2), Factor(3))))

  println("3 + 4 = ")
  println(plus(3, 4))

  println("3 |+| 4 = ")
  println(3 |+| 4)

  println("RPN calculator: ")
  println(RPNCalculator.solveRPN("10 4 3 + 2 * -"))


  println("extract http response")
  println(TaskDemo.get("https://api.mazeforce.com").unsafePerformAsync(r => {
    println(r)
  }))

}

