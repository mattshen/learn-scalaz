package example

import example.Monoid.Factor

import scalaz._

//Monoid type class
trait Monoid[A] {
  def mappend(a1: A, a2: A): A
  def mzero: A
}

//type class instances
object Monoid {

  sealed trait Factor
  def Factor[A](a: A): A @@ Factor = Tag[A, Factor](a)

  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a + b
    def mzero: Int = 0
  }
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    def mappend(a: String, b: String): String = a + b
    def mzero: String = ""
  }

  implicit val multiMonoid: Monoid[Int @@ Factor] = new Monoid[Int @@ Factor] {
    def mappend(a: Int @@ Factor, b: Int @@ Factor): Int @@ Factor = Factor(Tag.unwrap(a) * Tag.unwrap(b))
    def mzero: Int @@ Factor = Factor(1)
  }

}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit  val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
    override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft (b) (f)
  }
}

trait MonoidOp[A] {
  val F: Monoid[A]
  val value: A
  def |+|(a2: A) = F.mappend(value, a2)
}

object MonoidOp {
  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    override val F = implicitly[Monoid[A]]
    override val value = a
  }
}

object Sums {

  /*
  def sum[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }
  */

  def sum[M[_]: FoldLeft, A: Monoid](xs: M[A]) : A = {
    val fl = implicitly[FoldLeft[M]]
    val m = implicitly[Monoid[A]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  def plus[A: Monoid](a: A, b: A): A = implicitly[Monoid[A]].mappend(a, b)

}



