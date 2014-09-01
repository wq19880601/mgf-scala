package com.mgf.facade

import com.mgf.core.domain.{CreateActiveMemberDto, ResultDto}
import com.mgf.exception.MemberGatewayInvokeFailureException

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月12日 15:21 wangqiang$
 */
trait MemberGatewayFront {

  /**
   * active the users's account
   * @param activeMemberDto
   * @throws core.exception.MemberGatewayInvokeFailureException
   * @return
   */
  @throws(classOf[MemberGatewayInvokeFailureException])
  def createActiveMember(activeMemberDto: Option[CreateActiveMemberDto]): ResultDto[Unit]


}
