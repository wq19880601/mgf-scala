package com.mgf.facade.impl

import com.mgf.core.domain.CreateActiveMemberDto
import com.mgf.core.enums.{IdTypeEnum, MemberTypeEnum}
import com.mgf.facade.MemberGatewayFront
import org.junit.Test
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.immutable


/**
 * <p>mgf cient test</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月25日 11:14 wangqiang$
 */
class MemberClientTest {

  @Test def testCreateActiveMember(){
    lazy val applicationContext = new ClassPathXmlApplicationContext("classpath:spring-test.xml")

    val memberGatewayFront: MemberGatewayFront = applicationContext.getBean("memberClient").asInstanceOf[MemberGatewayFront]

    val createActiveMemberDto = new CreateActiveMemberDto()
    createActiveMemberDto.extendParam = generateExtendParams()
    createActiveMemberDto.identityId = "test41"
    createActiveMemberDto.idType = Some(IdTypeEnum.UID)
    createActiveMemberDto.memberType = Some(MemberTypeEnum.PERSONAL)

    memberGatewayFront.createActiveMember(Some(createActiveMemberDto))
  }


  def generateExtendParams(): Map[String, String] = {
    immutable.HashMap(("aaaa" -> "cdefff"), ("acd" -> "11111"))
  }
}
