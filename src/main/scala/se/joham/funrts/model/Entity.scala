package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
trait Entity {
  def id: String
  def name: String
  def pos: Pos
  def size: Size
}