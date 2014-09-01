package com.mgf.core.domain

/**
 * <p>redult data transfer object</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 17:29 wangqiang$
 */
class ResultDto[A](var success: Boolean = false) extends Serializable {

  // main business entity
  var module = None: Option[A]

  // error code
  var errorCode: String = _

  // error message
  var errorMsg: String = _

  // the count of the result
  var totalCount: Int = 0

  // memo for business description
  var memo: String = _

  override def toString = {
    val resultInfo = new StringBuilder
    resultInfo ++= s"resultDto, result=[$success], totalCount=[$totalCount],memo=[$memo]"
    resultInfo ++= s",errorCode=[$errorCode]" ++= s",errorMsg=[$errorMsg]"
    resultInfo.toString
  }
}
