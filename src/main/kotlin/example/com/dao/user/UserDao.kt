package example.com.dao.user

import example.com.model.SignUpParams
import example.com.model.UserInfo

interface UserDao {
    suspend fun insert(params: SignUpParams): UserRow?

    suspend fun insertUserInfo(userInfo : UserInfo): UserInfo?

    suspend fun findByEmail(email: String): UserRow?

    suspend fun findById(userId: Long): UserRow?

    suspend fun updateUser(userId: Long, name: String, bio: String, imageUrl: String?): Boolean

    suspend fun updateUser(userId: Long, userInfo: UserInfo): Boolean

    suspend fun getUsers(ids: List<Long>): List<UserRow>

}