package se.joham.funrts.model

import java.util.UUID

/**
  * Created by johan on 2016-06-11.
  */
object Id {
  def gen(): Id = UUID.randomUUID().toString
}
