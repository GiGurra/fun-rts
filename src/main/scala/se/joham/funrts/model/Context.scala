package se.joham.funrts.model

import se.joham.funrts.scalego.CEStore

/**
  * Created by johan on 2016-06-26.
  */
case class Context(store: CEStore[Context], terrain: Terrain) {
  implicit def _store: CEStore[Context] = store
  implicit def _terrain: Terrain = terrain
}
