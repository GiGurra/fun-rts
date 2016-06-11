package se.joham.funrts.math
import Vec2FixPt._

/**
  * Created by johan on 2016-06-11.
  */
case class Vec2FixPt(x: Long = 0L, y: Long = 0L) {

  def +(other: Vec2FixPt) = Vec2FixPt(x + other.x, y + other.y)
  def -(other: Vec2FixPt) = Vec2FixPt(x - other.x, y - other.y)
  def isWithin(maxDelta: Long, otherPosition: Vec2FixPt) = distance(this, otherPosition) <= maxDelta
  def *(other: Vec2FixPt) = x * other.x + y * other.y
  def *(k: Long): Vec2FixPt = Vec2FixPt(x * k, y * k)
  def /(d: Long): Vec2FixPt = {
    require(d != 0, "Cannot divide vector by zero")
    Vec2FixPt(x / d, y / d)
  }
  def +(d: Long): Vec2FixPt = Vec2FixPt(x + d, y + d)
  def -(d: Long): Vec2FixPt = Vec2FixPt(x - d, y - d)
  def length = distance(zero, this)
  def unary_- : Vec2FixPt = this * (-1)
  def isZero = x == 0L && y == 0L
  def normalized(newLength: Long, acceptZeroLength: Boolean): Vec2FixPt = {
    if (isZero && acceptZeroLength) {
      Vec2FixPt.zero
    } else {
      Vec2FixPt.normalized(this, newLength)
    }
  }
}

object Vec2FixPt {

  val zero = Vec2FixPt(0L, 0L)

  def normalized(_v: Vec2FixPt, newLength: Long): Vec2FixPt = {
    require(newLength < Int.MaxValue / 2, "Arithmetic issue - cannot normalize to lenth > Int.MaxValue / 2")
    require(_v.length < Int.MaxValue / 2, "Arithmetic issue - cannot normalize if v.length > Int.MaxValue / 2")
    require(!_v.isZero, "Cannot normalize a zero vector")

    val upScale = Int.MaxValue / 4 / _v.length
    val v = _v * upScale
    v * newLength / v.length
  }

  def distance(a: Vec2FixPt, b: Vec2FixPt): Long = {

    val dx = a.x - b.x
    val dy = a.y - b.y

    require(dx < Int.MaxValue / 2, "Arithmetic issue - cannot normalize to dx > Int.MaxValue / 2")
    require(dy < Int.MaxValue / 2, "Arithmetic issue - cannot normalize to dx > Int.MaxValue / 2")

    val sumSquare = dx * dx + dy * dy
    FixPt.sqrt(sumSquare)
  }

  implicit class RichLongForWorldVector(val x: Long) extends AnyVal {
    def *(v: Vec2FixPt) = v * x
    def +(v: Vec2FixPt) = Vec2FixPt(x, x) + v
    def -(v: Vec2FixPt) = Vec2FixPt(x, x) - v
  }

  implicit class RichIntForWorldVector(val x: Int) extends AnyVal {
    def *(v: Vec2FixPt) = v * x.toLong
    def +(v: Vec2FixPt) = Vec2FixPt(x.toLong, x.toLong) + v
    def -(v: Vec2FixPt) = Vec2FixPt(x.toLong, x.toLong) - v
  }
}
