package jp.matsuura.di

import jp.matsuura.service.AuthService
import jp.matsuura.service.AuthServiceImpl
import org.koin.dsl.module

val authServiceModule = module {
    single<AuthService> { AuthServiceImpl() }
}