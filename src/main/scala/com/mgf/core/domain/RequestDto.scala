package com.mgf.core.domain

import com.mgf.core.enums.CharsetTypeEnum.UTF8

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 15:45 wangqiang$
 */
class RequestDto {

  var charsetType = Some(UTF8)

  var memo = None: Option[String]
}
