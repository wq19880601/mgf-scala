package com.mgf.core.enums

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 15:40 wangqiang$
 */
object MemberTypeEnum {

  sealed abstract class MemberType {
    val memberType: Int
  }

  case object PERSONAL extends MemberType {
    val memberType = 1
  }

  case object ENTERPRISE extends MemberType {
    val memberType = 2
  }

}
