package jp.matsuura.di

import jp.matsuura.data.repository.auth.AuthRepository
import jp.matsuura.data.repository.auth.AuthRepositoryImpl
import jp.matsuura.data.repository.user.UserRepository
import jp.matsuura.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val authRepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
}

val userRepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}