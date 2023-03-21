package jp.matsuura.service

import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.utility.Result

interface AuthService {
    fun login(email: String, password: String) : Result<AuthResponse, LoginErrorType>
    fun register(email: String, password: String) : Result<AuthResponse, RegisterErrorType>
}