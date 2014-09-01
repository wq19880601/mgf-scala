package com.mgf.facade.impl

import java.text.SimpleDateFormat
import java.util.Date

import com.mgf.core.domain.MgfConstants._
import com.mgf.core.domain.{CreateActiveMemberDto, RequestDto, ResponseCode, ResultDto}
import com.mgf.core.util.{MgfUtils, Request, SignInfo}
import com.mgf.exception.MemberGatewayInvokeFailureException
import com.mgf.facade.MemberGatewayFront
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json._

import scala.beans.BeanProperty
import scala.collection.mutable

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月20日 14:45 wangqiang$
 */
class MemberClient extends MemberGatewayFront {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[MemberClient])


  // md5 key path
  @BeanProperty
  var memberMd5KeyPath: String = _
  // member public key
  @BeanProperty
  var memberPublicKey: String = _
  // member gateWay url
  @BeanProperty
  var memberGatewayUrl: String = _
  // member sign version
  @BeanProperty
  var memberDefaultSignVersion: String = _
  // member encypt version
  @BeanProperty
  var memberDefaultEncyptVersion: String = _
  // member server version
  @BeanProperty
  var memberDefaultServerVersion: String = _
  // member parterId
  @BeanProperty
  var memberDefaultParterId: String = _

  // member md5 key
  var memberMd5Key = None: Option[String]


  def init: Unit = {
    memberMd5Key = MgfUtils.readTextFile(memberMd5KeyPath)
  }


  @throws(classOf[MemberGatewayInvokeFailureException])
  def createActiveMember(activeMemberDto: Option[CreateActiveMemberDto]): ResultDto[Unit] = {

    try {
      val resultDto = new ResultDto[Unit]

      // check object null or not
      if (activeMemberDto.isEmpty) throw new IllegalArgumentException("activeMemberDto is null")

      // build request params
      val requestMap = mutable.HashMap[String, String]()
      val resultActiveMember = activeMemberDto.get
      addBaseparams(requestMap, SERVICE_CREATE_ACTIVE_MEMBER, resultActiveMember)
      addActiveMemberParams(requestMap, resultActiveMember)

      if (logger.isInfoEnabled) logger.info("createActiveMember, request params is {}", requestMap)

      val charset: String = activeMemberDto.get.charsetType.get.charset
      request(charset, requestMap.toMap, resultDto, null)

      resultDto
    } catch {
      case e: Exception => throw new MemberGatewayInvokeFailureException(e)
    }
  }


  /**
   * request to the gateway, resolve response, set resultDto info
   * @param charset the charset type
   * @param requestMap request data
   * @param resultDto
   * @param f call method when response have return value
   * @tparam A
   * @return
   */
  def request[A](charset: String, requestMap: Map[String, String], resultDto: ResultDto[A],
                 f: String => Unit) = {
    // send request and parse json result
    val signInfo = new SignInfo {
      var key: String = memberMd5Key.get
      var version: String = memberDefaultSignVersion
    }
    val result = new Request().post(memberGatewayUrl).charset(charset).signInfo(signInfo).data(requestMap).execute()

    val jsonVal: JsValue = Json.parse(result)
    val responseCode = (jsonVal \ RESPNOSE_CODE).asOpt[String]

    require(responseCode.isDefined, "no response from member gateway")

    // result, true or false
    val isSuccess: Boolean = responseCode match {
      case _ if ResponseCode.APPLY_SUCCESS.toString.equals(responseCode) => true
      case _ if ResponseCode.BIZ_PENDING.toString.equals(responseCode) => true
      case _ => false
    }

    if (isSuccess) {
      MgfUtils.verifySign(result, charset, memberMd5Key.get)
      if (f == null) resultDto.success = true
      else f(result)
    } else {
      resultDto.errorCode = (jsonVal \ RESPNOSE_CODE).asOpt[String].getOrElse("")
      resultDto.errorMsg = (jsonVal \ RESPNOSE_MESSAGE).asOpt[String].getOrElse("")
    }
  }


  /**
   * add active member params
   * @param requestMap
   * @param resultActiveMember
   */
  def addActiveMemberParams(requestMap: mutable.HashMap[String, String],
                            resultActiveMember: CreateActiveMemberDto) = {

    requestMap += (IDENTITY_ID -> resultActiveMember.identityId)
    requestMap += (IDENTITY_TYPE -> resultActiveMember.idType.get.name)

    if (!resultActiveMember.memberType.isEmpty) {
      requestMap += (MEMBER_TYPE -> resultActiveMember.memberType.get.memberType.toString)
    }
    if (!resultActiveMember.extendParam.isEmpty && !resultActiveMember.extendParam.isEmpty) {
      requestMap += (EXTEND_PARAM -> MgfUtils.convert2String(resultActiveMember.extendParam))
    }
  }


  /**
   * add base params to the dest map
   * @param requestDto
   * @return
   */
  def addBaseparams(seqParam: mutable.HashMap[String, String], service: String,
                    requestDto: RequestDto): Unit = {

    val requestTime: String = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date)

    val resultMap = Array(SERVICE, VERSION, REQUEST_TIME, PARTNER_ID, INPUT_CHARSET, ENCRYPT_VERSION).
      zip(Array(service, memberDefaultServerVersion, requestTime, memberDefaultParterId,
      requestDto.charsetType.get.charset, memberDefaultEncyptVersion)).toMap

    if (!requestDto.memo.isEmpty && !requestDto.memo.get.isEmpty) resultMap + (MEMO -> requestDto.memo.get)

    seqParam ++= resultMap
  }
}
