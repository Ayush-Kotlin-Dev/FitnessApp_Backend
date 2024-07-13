package example.com.repository

import example.com.model.*
import example.com.util.Response


interface AuthRepository {
    suspend fun signUp(signUpParams: SignUpParams): Response<AuthResponseData>
    suspend fun signIn(signInParams: SignInParams): Response<AuthResponseData>
}