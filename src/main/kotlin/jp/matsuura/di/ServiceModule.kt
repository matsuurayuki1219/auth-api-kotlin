package jp.matsuura.di

import jp.matsuura.service.auth.AuthService
import jp.matsuura.service.auth.AuthServiceImpl
import jp.matsuura.service.user.UserService
import jp.matsuura.service.user.UserServiceImpl
import org.koin.dsl.module

val authServiceModule = module {
    single<AuthService> { AuthServiceImpl() }
}

val userServiceModule = module {
    single<UserService> { UserServiceImpl() }
}