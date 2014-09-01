package com.mgf.core.util

import org.apache.commons.codec.digest.DigestUtils

/**
 * <p>md5 util, encrypt or decrypt the destination String content</p>
 * @author Wallis Wang
 * @version $Id:v0.1 2014年08月22日 10:22 wangqiang$
 */
object Md5Util {

  // covnert string to bytes array with charset
  private val bytesByCharset = (content: String, charset: String) =>
    if (charset.isEmpty) content.getBytes else content.getBytes(charset)

  // sing text and key by the charset
  def sign(text: String, key: String, charset: String): String = {
    DigestUtils.md5Hex(bytesByCharset(text + key, charset))
  }


  // compare text and key sign content with the specified content
  def verify(text: String, key: String, charset: String, sign: String): Boolean =
    if (bytesByCharset(text + key, charset).equals(sign)) true else false
}
