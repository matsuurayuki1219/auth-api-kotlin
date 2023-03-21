package jp.matsuura.service

import jp.matsuura.data.entity.Token
import jp.matsuura.data.entity.Tokens
import jp.matsuura.data.entity.User
import jp.matsuura.data.repository.AuthRepository
import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.CommonUtils
import jp.matsuura.utility.Result
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
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

        if (!CommonUtils.checkValidationOfEmail(email = email)) {
            return Result.Failure(error = RegisterErrorType.EmailValidationError)
        }
        if (!CommonUtils.checkValidationOfPassword(password = password)) {
            return Result.Failure(error = RegisterErrorType.PasswordValidationError)
        }

        val user = authRepository.findByEmail(email = email)
        if (user != null) {
            return Result.Failure(error = RegisterErrorType.AlreadyExistedUser)
        }

        val accessToken = "test"
        val refreshToken = "test"

        val insertUserData = User(
            email = email,
            password = password,
            createdAt = DateTime.now(),
            updatedAt = DateTime.now(),
        )

        val insertTokenData = Token(
            email = email,
            refreshToken = refreshToken,
            createdAt = DateTime.now(),
            updatedAt = DateTime.now(),
        )

        transaction {
            authRepository.insertUser(insertUserData)
            authRepository.insertToken(insertTokenData)
        }

        val response = AuthResponse(
            message = "OK",
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
        return Result.Success(value = response)
    }
}