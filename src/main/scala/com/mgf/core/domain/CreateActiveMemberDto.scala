package com.mgf.core.domain

import com.mgf.core.enums.IdTypeEnum.IdType
import com.mgf.core.enums.MemberTypeEnum.MemberType

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 16:11 wangqiang$
 */
class CreateActiveMemberDto extends RequestDto with Serializable {

  /**
   * identity id
   */
  var identityId: String = _

  /**
   * id type
   */
  var idType = None: Option[IdType]

  /**
   * member type
   */
  var memberType = None: Option[MemberType]

  /**
   * extend params
   */
  var extendParam: Map[String, String] = _
}
