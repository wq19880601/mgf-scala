package com.mgf.core.enums

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 15:40 wangqiang$
 */
object IdTypeEnum {

  sealed abstract class IdType {
    val name: String
  }

  case object UID extends IdType {
    val name: String = "UID"
  }

  case object MOBILE extends IdType {
    val name: String = "MOBILE"

  }

  case object EMAIL extends IdType {
    val name: String = "EMAIL"
  }

}
