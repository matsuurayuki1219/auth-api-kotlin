package jp.matsuura.di

import jp.matsuura.service.login.LoginService
import jp.matsuura.service.login.LoginServiceImpl
import jp.matsuura.service.register.RegisterService
import jp.matsuura.service.register.RegisterServiceImpl
import org.koin.dsl.module

val loginServiceModule = module {
    single<LoginService> { LoginServiceImpl() }
}

val registerServiceModule = module {
    single<RegisterService> { RegisterServiceImpl() }
}