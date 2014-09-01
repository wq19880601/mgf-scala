package com.mgf.core.util

import java.net.SocketTimeoutException

import com.mgf.core.domain.MgfConstants
import org.apache.http._
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.conn.ConnectTimeoutException
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair
import org.apache.http.protocol.HTTP
import org.apache.http.util.EntityUtils

import scala.collection.JavaConversions._

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月22日 14:45 wangqiang$
 */
object HttpProtocolHandler {

  // connect config
  final val DEFAULT_MAX_TOTAL_CONN = 200
  final val DEFAULT_MAX_PER_ROUTE = 20
  final val DEFAULT_MAX_PERROUTE = 50

  // timeout config
  final val SOCKET_TIMEOUT = 10000
  final val CLIENT_CONNECT_TIMEOUT = 10000
  final val CLIENT_REQEUST_TIMEOUT = 10000

  final val DEFAULT_CHARSET = "utf-8"

  // request timeout config
  private val requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
    .setConnectTimeout(CLIENT_CONNECT_TIMEOUT).setConnectionRequestTimeout(CLIENT_REQEUST_TIMEOUT).build()

  // pool manager configuration
  private def initPoolManager: PoolingHttpClientConnectionManager = {
    val poolManager = new PoolingHttpClientConnectionManager
    poolManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONN)
    poolManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE)
    poolManager
  }

  private def nameValPair(params: Map[String, String]): List[BasicNameValuePair] = {
    for ((k, v) <- params) yield new BasicNameValuePair(k, v)
  }.toList

  /**
   * client communicate with the server by using the get method
   * @param url
   * @param charset
   * @param params
   */
  def get(url: String, charset: String, params: Map[String, String]): String = {
    val client = buildHttpClient(false)

    // get method config
    val result = new StringBuilder(url) ++ MgfConstants.QM ++ URLEncodedUtils.format(nameValPair(params), charset)
    val httpGet = new HttpGet(result.toString())
    httpGet.setConfig(requestConfig)

    // interact with the server
    val response = client.execute(httpGet)
    checkResponseStatus(response)

    // process the response
    if (response.getEntity == null) ""
    else EntityUtils.toString(response.getEntity, charset)
  }


  /**
   * client communicate with the server by using the post method
   * @param url
   * @param charset
   * @param params
   * @throws org.apache.http.HttpException
   * @return
   */
  @throws(classOf[ConnectTimeoutException])
  @throws(classOf[SocketTimeoutException])
  @throws(classOf[HttpException])
  def post(url: String, charset: String, params: Map[String, String]): String = {

    try {
      val client = buildHttpClient(false)

      // post method config
      val httpPost = new HttpPost(url)
      httpPost.setConfig(requestConfig)
      httpPost.setHeader(HTTP.CONTENT_ENCODING, charset)

      httpPost.setEntity(new UrlEncodedFormEntity(nameValPair(params), charset))

      // interact with the server
      val response = client.execute(httpPost)

      // process the response
      checkResponseStatus(response)
      if (response.getEntity == null) ""
      else EntityUtils.toString(response.getEntity, charset)
    }
    catch {
      case e: ConnectTimeoutException => throw new ConnectTimeoutException("connect to geteway")
      case e: SocketTimeoutException => throw new SocketTimeoutException("socket timeout")
      case e: Exception => throw new HttpException("unknow network error")
    }

  }

  /**
   * check the stauts code, if not 200 then throw a httpException
   * @param response
   */
  private def checkResponseStatus(response: HttpResponse) = {
    // response form the server
    val statusCode = response.getStatusLine.getStatusCode
    statusCode match {
      case HttpStatus.SC_OK =>
      case _ => throw new HttpException(s"incorrect status, code=[$statusCode]")
    }
  }


  /**
   * create the http client
   * @param isMultiThread
   * @return
   */
  private def buildHttpClient(isMultiThread: Boolean): HttpClient = {
    if (isMultiThread) HttpClients.custom().setConnectionManager(initPoolManager).build()
    else HttpClients.createDefault()
  }
}

