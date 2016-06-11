package se.joham.funrts

import se.joham.funrts.math.Vec2FixPt
import se.joham.funrts.util.Bits

/**
  * Created by johan on 2016-06-11.
  */
package object model {
  type Pos = Vec2FixPt
  type Size = Vec2FixPt
  type Cell = Byte

  implicit class RichCell(val cell: Byte) extends AnyVal {
    def hasUnit            : Boolean = Bits.has(cell, Cell.UNIT_OCC_BIT)
    def isType(t: Byte)    : Boolean = (cell & Cell.TYPE_BITS) == t
    def isWalkable         : Boolean = !hasUnit && (isType(Cell.TYPE_GROUND) || isType(Cell.TYPE_BRIDGE))
    def isChoppable        : Boolean = isType(Cell.TYPE_TREE)
    def isChopped          : Boolean = isType(Cell.TYPE_X_TREE)
    def isWater            : Boolean = isType(Cell.TYPE_WATER)
    def isCoast            : Boolean = isType(Cell.TYPE_COAST)
  }
}
