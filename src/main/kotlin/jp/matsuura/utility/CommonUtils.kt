package jp.matsuura.utility


object CommonUtils {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun checkValidationOfEmail(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email)
    }

    fun checkValidationOfPassword(password: String): Boolean {
        return password.length >= 8
    }
}