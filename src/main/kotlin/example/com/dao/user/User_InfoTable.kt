package example.com.dao.user

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object User_InfoTable: Table(name = "user_info"){
    val userId = long(name = "user_id").references(UserTable.userId , onDelete = ReferenceOption.CASCADE)
    val fullName = varchar(name = "full_name", length = 250)
    val age = integer(name = "user_age")
    val gender = varchar("gender", 50)
    val height = float("height")
    val weight = float("weight")
    val fitnessGoals = varchar("fitness_goals", 255)
    val activityLevel = varchar("activity_level", 255)
    val dietaryPreferences = varchar("dietary_preferences", 255)
    val workoutPreferences = varchar("workout_preferences", 255)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(UserTable.userId)
}