package example.com.dao.user

import example.com.dao.DatabaseFactory.dbQuery
import example.com.model.SignUpParams
import example.com.model.UserInfo
import example.com.plugins.hashPassword
import example.com.util.IdGenerator
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.transactions.transaction

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): UserRow? {
        return dbQuery {
            val insertStatement = UserTable.insert {
                it[id] = IdGenerator.generateId()
                it[userName] = params.userName
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun insertUserInfo(userInfo: UserInfo): UserInfo? {
        return dbQuery {
            val insertStatement = User_InfoTable.insert {
                it[userId] = userInfo.userId
                it[fullName] = userInfo.fullName
                it[age] = userInfo.age
                it[gender] = userInfo.gender
                it[height] = userInfo.height
                it[weight] = userInfo.weight
                it[fitnessGoals] = userInfo.fitnessGoals
                it[activityLevel] = userInfo.activityLevel
                it[dietaryPreferences] = userInfo.dietaryPreferences
                it[workoutPreferences] = userInfo.workoutPreferences
            }
            UserTable.update(where = { UserTable.id eq userInfo.userId }) {
                it[is_form_filled] = true
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUserInfo(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): UserRow? {
        return dbQuery {
            UserTable.select { UserTable.email eq email }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun findById(userId: Long): UserRow? {
        return dbQuery {
            UserTable.select { UserTable.id eq userId }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun updateUser(userId: Long, name: String, bio: String, imageUrl: String?): Boolean {
        return dbQuery {
            UserTable.update(where = { UserTable.id eq userId }) {
                it[userName] = name
                it[UserTable.bio] = bio
                it[UserTable.imageUrl] = imageUrl
            } > 0
        }
    }

    override suspend fun updateUser(userId: Long, userInfo: UserInfo): Boolean {
        return dbQuery {
            User_InfoTable.update(where = { User_InfoTable.userId eq userId }) {
                it[fullName] = userInfo.fullName
                it[age] = userInfo.age
                it[gender] = userInfo.gender
                it[height] = userInfo.height
                it[weight] = userInfo.weight
                it[fitnessGoals] = userInfo.fitnessGoals
                it[activityLevel] = userInfo.activityLevel
                it[dietaryPreferences] = userInfo.dietaryPreferences
                it[workoutPreferences] = userInfo.workoutPreferences
            } > 0
        }
    }

    override suspend fun getUsers(ids: List<Long>): List<UserRow> {
        return dbQuery {
            UserTable.select(where = { UserTable.id inList ids })
                .map { rowToUser(it) }
        }
    }
}


private fun rowToUser(row: ResultRow): UserRow {
    return UserRow(
        id = row[UserTable.id],
        userName = row[UserTable.userName],
        bio = row[UserTable.bio],
        imageUrl = row[UserTable.imageUrl],
        password = row[UserTable.password],
        email = row[UserTable.email],
    )
}

private fun rowToUserInfo(row: ResultRow): UserInfo {
    return UserInfo(
        userId = row[User_InfoTable.userId],
        fullName = row[User_InfoTable.fullName],
        age = row[User_InfoTable.age],
        gender = row[User_InfoTable.gender],
        height = row[User_InfoTable.height],
        weight = row[User_InfoTable.weight],
        fitnessGoals = row[User_InfoTable.fitnessGoals],
        activityLevel = row[User_InfoTable.activityLevel],
        dietaryPreferences = row[User_InfoTable.dietaryPreferences],
        workoutPreferences = row[User_InfoTable.workoutPreferences]
    )
}









