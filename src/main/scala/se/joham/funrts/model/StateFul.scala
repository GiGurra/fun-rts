package se.joham.funrts.model
import scala.language.implicitConversions

/**
  * Created by johan on 2016-06-11.
  */
case class StateFul[T](var state: T) {
  def apply(f: T => T): StateFul[T] = mutate(f)
  def mutate(f: T => T): StateFul[T] = {
    this.state = f(this.state)
    this
  }
  def update(state: T): StateFul[T] = {
    this.state = state
    this
  }
}

object StateFul {
  implicit def s2t[T](s: StateFul[T]): T = s.state
}
