package com.mgf.core.util

import java.net.{URLDecoder, URLEncoder}

import com.mgf.core.domain.MgfConstants
import com.mgf.core.enums.CharsetTypeEnum.UTF8

import scala.collection.mutable

/**
 * <p>gateway network request utils</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月21日 16:51 wangqiang$
 */
class Request {

  private var inputCharset: String = _

  private var url: String = _

  private var requestMethod: String = _

  private var requestData: Map[String, String] = _

  private var signInfo = None: Option[SignInfo]


  def charset(inputCharset: String): this.type = {
    this.inputCharset = inputCharset
    this
  }

  def post(url: String): this.type = {
    this.url = url
    this.requestMethod = Request.METHOD_POST
    this
  }

  def get(url: String): this.type = {
    this.url = url
    this.requestMethod = Request.METHOD_GET
    this
  }

  def data(requestData: Map[String, String]): this.type = {
    this.requestData = requestData
    this
  }

  def signInfo(signInfo: SignInfo): this.type = {
    this.signInfo = Some(signInfo)
    this
  }


  def execute(): String = {
    require(signInfo.isDefined, "no sign info")

    val requestParams = Request.buildRequestParams(requestData, signInfo.get.key, signInfo.get.version, inputCharset)
    val response = HttpProtocolHandler.post(url, inputCharset, requestParams)
    URLDecoder.decode(response, inputCharset)
  }


}

object Request {


  final val METHOD_GET = "get"

  final val METHOD_POST = "post"


  // filter value in the map, which is null or empty, or about the sign info
  val filterValues = (value: String) => {
    if (null == value || value.isEmpty) false
    else {
      value match {
        case _ if value.equalsIgnoreCase(MgfConstants.SIGN) => false
        case _ if value.equalsIgnoreCase(MgfConstants.SIGN_TYPE) => false
        case _ => true
      }
    }
  }

  /**
   * create linked String from the sParamsTmp
   * <pre>
   * 1、sort the map by key
   * 2、cycle the map, then join entry with =
   * 3、join the result with && symbol
   * </pre>
   * @param sParamsTmp
   * @param encode
   * @return
   */
  def createLinkdedString(sParamsTmp: Map[String, String], encode: Boolean): String = {

    val requestCharset = sParamsTmp.getOrElse(MgfConstants.INPUT_CHARSET, UTF8.charset)
    val sParamsTempSorted = mutable.LinkedHashMap(sParamsTmp.toSeq.sortBy(_._1): _*)

    val paramsLinked = for ((k, v) <- sParamsTempSorted) yield {
      val encodeVal = if (encode) URLEncoder.encode(v, requestCharset) else v
      k + MgfConstants.EQS + encodeVal
    }
    paramsLinked.mkString(MgfConstants.ANDS)
  }


  /**
   * build request params
   * @param sParamsTemp
   * @param key
   * @param version
   * @param inputCharset
   * @return
   */
  def buildRequestParams(sParamsTemp: Map[String, String],
                         key: String, version: String, inputCharset: String): Map[String, String] = {

    val sFilterParams = sParamsTemp.filter((t) => filterValues(t._2)).toMap
    val linkMapEntries = createLinkdedString(sFilterParams, false)

    val result = mutable.Map() ++ sFilterParams
    result += (MgfConstants.SIGN -> Md5Util.sign(linkMapEntries, key, inputCharset))
    result += (MgfConstants.SIGN_TYPE -> MgfConstants.MD5)
    result += (MgfConstants.SIGN_VERSION -> version)

    result.toMap
  }

}

