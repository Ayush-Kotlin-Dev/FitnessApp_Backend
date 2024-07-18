package example.com.repository

import example.com.model.UserInfo
import example.com.model.UserInfoResponseData
import example.com.util.Response

interface UserInfoRepository  {
    suspend fun insertUserInfo(userInfo : UserInfo): Response<UserInfoResponseData>

    suspend fun getUserInfo(userId: Long): Response<UserInfoResponseData>

    suspend fun updateUser( userInfo: UserInfo):  Response<UserInfoResponseData>
}