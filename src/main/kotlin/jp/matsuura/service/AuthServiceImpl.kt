package jp.matsuura.service

import jp.matsuura.data.User
import jp.matsuura.data.Users
import jp.matsuura.model.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.Result
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

class AuthServiceImpl : AuthService {

    override fun login(
        email: String,
        password: String,
        ): Result<AuthResponse, LoginErrorType> {

        val users = Users.select {Users.email eq email}.map { it.toUser() }
        if (users.isEmpty()) {
            return Result.Failure(error = LoginErrorType.NotExistUser)
        } else if (users.size > 1) {
            return Result.Failure(error = LoginErrorType.UnknownError)
        }

        val userInfo = users.first()
        if (userInfo.password != password) {
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

    private fun ResultRow.toUser(): User {
        return User(
            email = this[Users.email],
            password = this[Users.password],
            createdAt = this[Users.createdAt],
            updatedAt = this[Users.updatedAt],
        )
    }
}