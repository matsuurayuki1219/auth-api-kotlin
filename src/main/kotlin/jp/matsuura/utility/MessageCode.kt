package jp.matsuura.utility

object MessageCode {

    // Login
    const val ES01_001: String = "ES01_001"
    const val ES01_002: String = "ES01_002"
    const val ES01_003: String = "ES01_003"

    // Register
    const val ES02_001: String = "ES02_001"
    const val ES02_002: String = "ES02_002"
    const val ES02_003: String = "ES02_003"
    const val ES02_004: String = "ES02_004"

    val MessageMap = mapOf(
        // Login
        ES01_001 to "該当のユーザは存在しません。",
        ES01_002 to "想定外のエラーが発生しました。",
        ES01_003 to "パスワードが誤っています。",

        // Register
        ES02_001 to "すでにユーザ登録されています。",
        ES02_002 to "メールアドレスが不正です。",
        ES02_003 to "パスワードが不正です。",
        ES02_004 to "想定外のエラーが発生しました。",
    )
}