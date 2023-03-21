package jp.matsuura.di

import jp.matsuura.data.repository.AuthRepository
import jp.matsuura.data.repository.AuthRepositoryImpl
import jp.matsuura.service.AuthService
import jp.matsuura.service.AuthServiceImpl
import org.koin.dsl.module

val authRepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
}