package jp.matsuura.service.login

import jp.matsuura.model.request.LoginRequest

interface LoginService {
    fun login(model: LoginRequest)
}