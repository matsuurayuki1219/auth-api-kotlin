package jp.matsuura.utility

object MessageCode {

    // Login
    const val ES01_001: String = "ES01_001"
    const val ES01_002: String = "ES01_002"
    const val ES01_003: String = "ES01_003"

    val MessageMap = mapOf(
        ES01_001 to "該当のユーザは存在しません。",
        ES01_002 to "想定外のエラーが発生しました。",
        ES01_003 to "パスワードが誤っています。",
    )
}