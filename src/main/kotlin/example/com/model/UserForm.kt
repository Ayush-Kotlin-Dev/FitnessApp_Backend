package example.com.model

import kotlinx.serialization.Serializable



@Serializable
data class UserInfo(
    val userId : Long,
    val fullName : String,
    val age : Int,
    val gender  : String,
    val height : Float,
    val weight : Float,
    val fitnessGoals : String,
    val activityLevel : String,
    val dietaryPreferences : String,
    val workoutPreferences : String
)

@Serializable
data class UserInfoResponseData(
    val data: UserInfo? = null,
    val errorMessage: String? = null
)
