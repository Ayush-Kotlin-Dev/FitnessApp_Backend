package example.com.repository

import example.com.dao.user.UserDao
import example.com.model.*
import example.com.util.Response
import example.com.plugins.generateToken
import example.com.plugins.hashPassword
import io.ktor.http.*

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun signUp(signUpParams: SignUpParams): Response<AuthResponseData> {
        return if (userAlreadyExist(signUpParams.email)) {
            Response.Error(
                code = HttpStatusCode.Conflict,
                data = AuthResponseData(
                    errorMessage = "User already exist"
                )
            )
        } else {
            val insertedUser = userDao.insert(signUpParams)

            if (insertedUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponseData(
                        errorMessage = "Oops, sorry we could not register the user, try later!"
                    )
                )
            } else {
                Response.Success(
                    data = AuthResponseData(
                        data = User(
                            userId = insertedUser.id,
                            userName = insertedUser.userName,
                            token = generateToken(signUpParams.email),
                            email = insertedUser.email,
                            bio =  insertedUser.bio
                        )
                    )
                )
            }

        }
    }

    override suspend fun signIn(signInParams: SignInParams): Response<AuthResponseData> {
        val user = userDao.findByEmail(signInParams.email)

        return if (user == null) {
            Response.Error(
                code = HttpStatusCode.NotFound,
                data = AuthResponseData(
                    errorMessage = "Invalid credentials, no user with this email!"
                )
            )
        } else {
            val hashedPassword = hashPassword(signInParams.password)
            if (user.password != hashedPassword) {
                Response.Error(
                    code = HttpStatusCode.NotFound,
                    data = AuthResponseData(
                        errorMessage = "Invalid credentials, password does not match!"
                    )
                )
            } else {
                Response.Success(
                    data = AuthResponseData(
                        data = User(
                            userId = user.id,
                            userName = user.userName,
                            token = generateToken(signInParams.email),
                            email = user.email,
                            bio = user.bio
                        )
                    )
                )
            }
        }
    }

    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}