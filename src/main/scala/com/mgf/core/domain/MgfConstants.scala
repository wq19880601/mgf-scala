package com.mgf.core.domain

/**
 * <p>constant field names of mgf gateway</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月20日 16:43 wangqiang$
 */
object MgfConstants {

  //====================================
  // service interfaces
  //===================================
  final val ANGLE_BRACKETS = "^"

  final val UNDER_LINE = "_"

  final val VERTICAL_LINE = "|"

  final val DOLLAR = "$"

  final val WAVY_LINES = "~"

  final val COMMA = ","

  final val ANDS = "&&"

  final val EQS = "="

  final val QM = "?"

  //====================================
  // ~~ sign info
  //===================================
  final val SIGN = "sign"

  final val SIGN_TYPE = "sign_type"

  final val SIGN_VERSION = "sign_version"

  final val MD5 = "MD5"

  final val RSA = "RSA"


  //====================================
  // service interfaces
  //===================================

  final val SERVICE_CREATE_ACTIVE_MEMBER = "create_activate_member"


  //====================================
  // base service field names
  //===================================
  final val SERVICE = "service"

  final val VERSION = "version"

  final val REQUEST_TIME = "request_time"

  final val PARTNER_ID = "partner_id"

  final val INPUT_CHARSET = "_input_charset"

  final val ENCRYPT_VERSION = "encrypt_version"

  final val MEMO = "memo"

  //====================================
  // active member service field names
  //===================================
  final val IDENTITY_ID = "identity_id"

  final val IDENTITY_TYPE = "identity_type"

  final val MEMBER_TYPE = "member_type"

  final val EXTEND_PARAM = "extend_param"


  //====================================
  // response of gateway
  //===================================

  final val RESPNOSE_CODE = "response_code"
  final val RESPNOSE_MESSAGE = "response_message"
}
