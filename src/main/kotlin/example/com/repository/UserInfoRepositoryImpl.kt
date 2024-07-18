package example.com.repository

import example.com.dao.user.UserDao
import example.com.model.UserInfo
import example.com.model.UserInfoResponseData
import example.com.util.Response
import io.ktor.http.*

class UserInfoRepositoryImpl(
    private val userDao: UserDao
) : UserInfoRepository {
    override suspend fun insertUserInfo(userInfo: UserInfo): Response<UserInfoResponseData> {
        return if (userDao.insertUserInfo(userInfo) == null) {
            Response.Error(
                code = HttpStatusCode.InternalServerError,
                data = UserInfoResponseData(
                    errorMessage = "Oops, sorry we could not register the user info, try later!"
                )
            )
        } else {
            Response.Success(
                data = UserInfoResponseData(
                    data = userInfo
                )
            )
        }

    }

    override suspend fun getUserInfo(userId: Long): Response<UserInfoResponseData> {
        val userInfo = userDao.getUserInfo(userId)
        return if (userInfo == null) {
            Response.Error(
                code = HttpStatusCode.NotFound,
                data = UserInfoResponseData(
                    errorMessage = "User info not found"
                )
            )
        } else {
            Response.Success(
                data = UserInfoResponseData(
                    data = userInfo
                )
            )
        }
    }

    override suspend fun updateUser( userInfo: UserInfo): Response<UserInfoResponseData> {
        val updated = userDao.updateUserInfo(userInfo)
        return if (updated) {
            Response.Success(
                data = UserInfoResponseData(
                    data = userInfo
                )
            )
        } else {
            Response.Error(
                code = HttpStatusCode.InternalServerError,
                data = UserInfoResponseData(
                    errorMessage = "Could not update user info"
                )
            )
        }

    }
}