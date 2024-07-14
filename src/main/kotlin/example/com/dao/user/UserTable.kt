package example.com.dao.user

import org.jetbrains.exposed.sql.Table

object UserTable: Table(name = "users"){
    val userId = long(name = "user_id").autoIncrement()
    val userName =  varchar(name = "user_name", length = 250)
    val email = varchar(name = "user_email", length = 250)
    val bio = text(name = "user_bio").default(
        defaultValue = "Hey, what's up? Welcome to my Gym Profile!"
    )
    val password = varchar(name = "user_password", length = 100)
    val imageUrl = text(name = "image_url").nullable()
    val is_form_filled = bool(name = "is_form_filled").default(defaultValue = false)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(userId)
}

data class UserRow(
    val id: Long,
    val userName: String,
    val email: String,
    val bio: String,
    val imageUrl: String?,
    val password: String,
    val isFormFilled: Boolean
    //TODO
    //Token : String //Will implement later for Payload functionality
)















