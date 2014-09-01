package com.mgf.exception

/**
 * <p>mgf gateway exception</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月20日 15:06 wangqiang$
 */
class MemberGatewayInvokeFailureException(message: String, e: Throwable) extends Exception(message, e) {

  def this(message: String) = this(message, null)

  def this(e: Throwable) = this(null, e)
}
