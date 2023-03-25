package jp.matsuura.service.user

import jp.matsuura.model.error.GetUserErrorType
import jp.matsuura.model.response.UserResponse
import jp.matsuura.utility.Result

interface UserService {

    fun getInfo(email: String): Result<UserResponse, GetUserErrorType>

}