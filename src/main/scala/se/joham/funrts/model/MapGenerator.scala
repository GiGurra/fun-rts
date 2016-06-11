package se.joham.funrts.model

/**
  * Created by johan on 2016-06-11.
  */
trait MapGenerator {
  def apply(nx: Int, ny: Int, seed: String): Array[Cell]
}
