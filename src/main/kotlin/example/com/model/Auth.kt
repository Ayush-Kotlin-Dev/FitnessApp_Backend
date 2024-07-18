package example.com.model

import kotlinx.serialization.Serializable
//Signup  Section

@Serializable
data class SignUpParams(
    val userName: String,
    val email: String,
    val password: String
)
@Serializable
data class SignInParams(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponseData(
    val data: User? = null,
    val errorMessage: String? = null
)

@Serializable
data class User(
    val userId: Long,
    val userName: String,
    val email: String,
    val bio : String ,
    val token: String,
    val isFormFilled: Boolean = false
)


//Login Section
@Serializable
data class SignInResponse(
    val data: UserInfo? = null,
    val errorMessage: String? = null
)

