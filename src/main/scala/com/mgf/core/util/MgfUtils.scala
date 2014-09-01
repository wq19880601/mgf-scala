package com.mgf.core.util

import java.io.{FileNotFoundException, IOException}

import com.mgf.core.domain.MgfConstants
import play.api.libs.json._

import scala.io.Source


/**
 * Created by walis on 14-8-20.
 */
object MgfUtils {


  //==================================
  //~~~ function template
  //==================================
  def using[A <: {def close() : Unit}, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }

  //==================================
  //~~~ utilities
  //==================================


  /**
   * join each entry's key and value with  and then combine theme with |
   * @param paramMap
   * @return
   */
  def convert2String(paramMap: Map[String, String]): String = {
    {
      for ((k, v) <- paramMap) yield k + MgfConstants.ANGLE_BRACKETS + v
    }.mkString(MgfConstants.VERTICAL_LINE)
  }


  /**
   * read the destination file as String
   * @param fileName
   * @throws java.io.IOException
   * @throws java.io.FileNotFoundException
   * @return
   */
  @throws(classOf[IOException])
  @throws(classOf[FileNotFoundException])
  def readTextFile(fileName: String): Option[String] = {

    try {
      val fileContent = using(Source.fromFile(fileName)) { source =>
        source.mkString
      }
      Some(fileContent)
    }
    catch {
      case e: FileNotFoundException => throw new FileNotFoundException(s"can not find the file $fileName !")
      case e: IOException => throw new IOException(s"read file $fileName occur error !")
    }
  }

  /**
   * verify the md5 sign content from the gateway response
   * @param response
   * @param charset
   * @param md5Key
   * @return
   */
  def verifySign(response: String, charset: String, md5Key: String) = {

    val convertResult = Json.parse(response).asOpt[Map[String, String]]
    require(convertResult.isDefined, "convert json result failed")

    val result: Map[String, String] = {
      convertResult.get.filterKeys(key => key match {
        case MgfConstants.SIGN => false
        case MgfConstants.SIGN_TYPE => false
        case MgfConstants.SIGN_VERSION => false
        case _ => true
      })
    }

    val linkedResult: String = Request.createLinkdedString(result, false)
    val jsVal: JsValue = Json.parse(response)
    val verifyResult: Boolean = Md5Util.verify(linkedResult, md5Key, charset, (jsVal \ MgfConstants.SIGN).as[String])

    require(verifyResult, "verify gateway response failed")
  }

}
