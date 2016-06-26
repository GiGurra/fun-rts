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

  implicit class RichTile(val tile: Tile.Type) extends AnyVal {
    def isType(t: Byte)       : Boolean = (tile & Tile.TYPE_BITS) == t
    def isEitherType(t: Byte*): Boolean = t.exists(isType)
    def isEitherType(t: Iterable[Byte]): Boolean = t.exists(isType)
    def isWalkable            : Boolean = isGround || isBridge
    def isGround              : Boolean = isType(Tile.TYPE_GROUND)
    def isBridge              : Boolean = isType(Tile.TYPE_BRIDGE)
    def isChoppable           : Boolean = isType(Tile.TYPE_TREE)
    def isChopped             : Boolean = isType(Tile.TYPE_X_TREE)
    def isWater               : Boolean = isType(Tile.TYPE_WATER)
    def isCoast               : Boolean = isType(Tile.TYPE_COAST)
  }
}
