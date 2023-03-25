package jp.matsuura.utility

import junit.framework.TestCase
import org.junit.Test

class CommonUtilsTest {

    @Test
    fun testEmailValidation_1() {
        val email = "test+1@example.com"
        TestCase.assertEquals(true, CommonUtils.checkValidationOfEmail(email = email))
    }

    @Test
    fun testEmailValidation_2() {
        val email = "test+1example.com"
        TestCase.assertEquals(false, CommonUtils.checkValidationOfEmail(email = email))
    }

    @Test
    fun testEmailValidation_3() {
        val email = "test+1example"
        TestCase.assertEquals(false, CommonUtils.checkValidationOfEmail(email = email))
    }

    @Test
    fun testEmailValidation_4() {
        val email = "@example"
        TestCase.assertEquals(false, CommonUtils.checkValidationOfEmail(email = email))
    }

    @Test
    fun testEmailValidation_5() {
        val email = "@example.co.jp"
        TestCase.assertEquals(false, CommonUtils.checkValidationOfEmail(email = email))
    }

}