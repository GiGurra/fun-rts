package se.joham.funrts.model

object Tile {
  type Type = Byte

  val TYPE_GROUND  = 0x00 : Byte
  val TYPE_WATER   = 0x01 : Byte
  val TYPE_COAST   = 0x02 : Byte
  val TYPE_TREE    = 0x03 : Byte
  val TYPE_X_TREE  = 0x04 : Byte
  val TYPE_BRIDGE  = 0x05 : Byte

  val TYPE_BITS    = 0x0F : Byte
}
