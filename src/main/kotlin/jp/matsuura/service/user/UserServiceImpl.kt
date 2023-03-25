package jp.matsuura.service.user

import jp.matsuura.data.repository.user.UserRepository
import jp.matsuura.model.error.GetUserErrorType
import jp.matsuura.model.response.UserResponse
import jp.matsuura.utility.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserServiceImpl: UserService, KoinComponent {

    private val userRepository by inject<UserRepository>()

    override fun getInfo(email: String): Result<UserResponse, GetUserErrorType> {
        val user = userRepository.findByEmail(email = email)
            ?: return Result.Failure(error = GetUserErrorType.NotExistUser)
        return Result.Success(value = UserResponse(email = user.email, password = user.password))
    }

}