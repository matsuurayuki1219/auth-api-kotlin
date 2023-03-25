package jp.matsuura.service.auth

import jp.matsuura.model.JwtInfo
import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.Result

interface AuthService {
    fun login(email: String, password: String, jwtInfo: JwtInfo) : Result<AuthResponse, LoginErrorType>
    fun register(email: String, password: String, jwtInfo: JwtInfo) : Result<AuthResponse, RegisterErrorType>
}