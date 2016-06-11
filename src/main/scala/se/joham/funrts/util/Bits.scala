package se.joham.funrts.util

/**
  * Created by johan on 2016-06-11.
  */
object Bits {
  def has(byte: Byte, bits: Byte): Boolean = (byte & bits) == bits

  val _0 = 0x01
  val _1 = 0x02
  val _2 = 0x04
  val _3 = 0x08
  val _4 = 0x10
  val _5 = 0x20
  val _6 = 0x40
  val _7 = 0x80
}
