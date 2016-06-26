package se.joham.funrts

import se.joham.funrts.math.Vec2FixPt

/**
  * Created by johan on 2016-06-11.
  */
package object model {
  type Pos = Vec2FixPt
  type EntityId = String
  type CombatantId = String
  type ComponentTypeId = String
  type PlayerId = String
  type TeamId = String
  type Size = Vec2FixPt
  type Tile = Byte
}
