package jp.matsuura.service.auth

import jp.matsuura.data.entity.User
import jp.matsuura.data.repository.auth.AuthRepository
import jp.matsuura.model.JwtInfo
import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.CommonUtils
import jp.matsuura.utility.Const
import jp.matsuura.utility.JwtUtils
import jp.matsuura.utility.Result
import org.joda.time.DateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthServiceImpl : AuthService, KoinComponent {

    private val authRepository by inject<AuthRepository>()

    override fun login(
        email: String,
        password: String,
        jwtInfo: JwtInfo,
        ): Result<AuthResponse, LoginErrorType> {

        val user = authRepository.findByEmail(email = email)
            ?: return Result.Failure(error = LoginErrorType.NotExistUser)

        if (user.password != password) {
            return Result.Failure(error = LoginErrorType.WrongPassword)
        }

        val accessToken = JwtUtils.generateAccessToken(
            audience = jwtInfo.audience,
            issuer = jwtInfo.issuer,
            email = email,
            secret = jwtInfo.secret,
        )

        val refreshToken = JwtUtils.generateRefreshToken(
            audience = jwtInfo.audience,
            issuer = jwtInfo.issuer,
            email = email,
            secret = jwtInfo.secret,
        )

        val response = AuthResponse(
            message = Const.OK,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
        return Result.Success(value = response)
    }

    override fun register(
        email: String,
        password: String,
        jwtInfo: JwtInfo,
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

        val accessToken = JwtUtils.generateAccessToken(
            audience = jwtInfo.audience,
            issuer = jwtInfo.issuer,
            email = email,
            secret = jwtInfo.secret,
        )

        val refreshToken = JwtUtils.generateRefreshToken(
            audience = jwtInfo.audience,
            issuer = jwtInfo.issuer,
            email = email,
            secret = jwtInfo.secret,
        )

        val insertUserData = User(
            email = email,
            password = password,
            createdAt = DateTime.now(),
            updatedAt = DateTime.now(),
        )

        authRepository.insertUser(insertUserData)

        val response = AuthResponse(
            message = Const.OK,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
        return Result.Success(value = response)
    }
}