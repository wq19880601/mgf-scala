package com.mgf.core.util

import org.scalatest.{FlatSpec, Matchers}

/**
 * <p></p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月22日 15:15 wangqiang$
 */
class HttpProtocolHandlerTest extends FlatSpec with Matchers {


  "http client test" should "have response" in {
    HttpProtocolHandler.post("http://www.youku.com", "utf-8", Map())
  }

}
