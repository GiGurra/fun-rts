package se.joham.funrts.util

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import com.google.common.io.ByteStreams

/**
  * Created by johan on 2016-06-12.
  */
object Zip {

  def compress(bytes: Array[Byte]): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val zs = new GZIPOutputStream(bos)
    zs.write(bytes)
    zs.close()
    bos.toByteArray
  }

  def decompress(bytes: Array[Byte]): Array[Byte] = {
    val bis = new ByteArrayInputStream(bytes)
    val zs = new GZIPInputStream(bis)
    ByteStreams.toByteArray(zs)
  }

}
