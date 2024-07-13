package example.com.di

import example.com.dao.user.UserDao
import example.com.dao.user.UserDaoImpl
import example.com.repository.AuthRepository
import example.com.repository.AuthRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single <UserDao>{ UserDaoImpl() }
    single <AuthRepository>{ AuthRepositoryImpl(get()) }
}