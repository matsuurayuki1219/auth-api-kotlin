package jp.matsuura.service

import jp.matsuura.data.repository.AuthRepository
import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthServiceImpl : AuthService, KoinComponent {

    private val authRepository by inject<AuthRepository>()

    override fun login(
        email: String,
        password: String,
        ): Result<AuthResponse, LoginErrorType> {

        val user = authRepository.findByEmail(email = email)
            ?: return Result.Failure(error = LoginErrorType.NotExistUser)

        if (user.password != password) {
            return Result.Failure(error = LoginErrorType.WrongPassword)
        }

        val response = AuthResponse(
            message = "OK",
            accessToken = "test",
            refreshToken = "test"
        )
        return Result.Success(value = response)
    }

    override fun register(
        email: String,
        password: String,
        ): Result<AuthResponse, RegisterErrorType> {
        TODO("Not yet implemented")
    }
}