package se.joham.funrts.model

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-11.
  */
case class Level(nx: Int, ny: Int, mesh: Array[Cell], entities: ArrayBuffer[Entity]) {

  def entitiesOfType[T <: Entity : ClassTag]: Seq[T] = entities.collect { case e: T => e }
  def buildings: Seq[Building] = entitiesOfType[Building]
  def characters: Seq[Character] = entitiesOfType[Character]
  def entity(id: Id): Option[Entity] = entities.find(_.id == id)
}

object Level {
  def apply(nx: Int,
            ny: Int,
            seed: String,
            generator: LevelGenerator): Level = {
    val mesh = generator.apply(nx, ny, seed)
    val entities = new ArrayBuffer[Entity]
    new Level(nx, ny, mesh, entities)
  }
}
