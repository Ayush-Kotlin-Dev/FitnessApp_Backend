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
data class AuthResponseData(
    val data: User? = null,
    val errorMessage: String? = null
)

@Serializable
data class User(
    val userId: Long,
    val userName: String,
    val email: String,
    val token: String
)


//Login Section
@Serializable
data class SignInResponse(
    val data: UserInfo? = null,
    val errorMessage: String? = null
)
@Serializable
data class SignInParams(
    val email: String,
    val password: String
)

@Serializable
data class UserInfo(
    val userId: Long,
    val fullName: String,
    val age: Int,
    val gender: String,
    val height: Float,
    val weight: Float,
    val fitnessGoals: String,
    val activityLevel: String,
    val dietaryPreferences: String,
    val workoutPreferences: String
)