package se.joham.funrts.model

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
  * Created by johan on 2016-06-11.
  */
class Map(nx: Int, ny: Int, seed: String, generator: MapGenerator) {
  private val mesh = generator.apply(nx, ny, seed)
  private val entities = new ArrayBuffer[Entity]

  def entitiesOfType[T <: Entity : ClassTag]: Seq[T] = entities.collect { case e: T => e }
  def buildings: Seq[Building] = entitiesOfType[Building]
  def characters: Seq[Character] = entitiesOfType[Character]

}
