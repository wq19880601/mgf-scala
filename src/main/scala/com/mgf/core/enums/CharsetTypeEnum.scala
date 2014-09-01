package com.mgf.core.enums

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 15:40 wangqiang$
 */
object CharsetTypeEnum {

  sealed abstract class CharsetType {
    val charset: String
  }

  case object UTF8 extends CharsetType {
    val charset = "utf-8"
  }

  case object GBK extends CharsetType {
    val charset = "gbk"
  }

  case object GB2312 extends CharsetType {
    val charset = "gb2312"
  }

}
