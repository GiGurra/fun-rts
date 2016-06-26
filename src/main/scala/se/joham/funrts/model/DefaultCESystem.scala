package se.joham.funrts.model

import se.joham.funrts.scalego.{CESystem, Component, Entity}

import scala.collection.mutable

/**
  * Created by johan on 2016-06-26.
  */
case class DefaultCESystem[T <: Component](entries: mutable.Map[Entity.Id, T] = mutable.Map.empty[Entity.Id, T]) extends CESystem[T, Context] {
  def duplicate: DefaultCESystem[T] = copy(entries.clone())
}

