package example.com.route

import example.com.model.UserInfo
import example.com.repository.AuthRepository
import example.com.repository.UserInfoRepository
import example.com.util.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.userInfoRouting(){
    val repository by inject<UserInfoRepository>()

    route(path = "/addUserInfo"){
        post {
            val params = call.receiveNullable<UserInfo>()
            if (params == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    message = "Invalid credentials!"
                )
            } else {
                when(val result = repository.insertUserInfo(params)){
                    is Response.Success -> {
                        call.respond(
                            result.code, result.data
                        )
                    }
                    is Response.Error -> {
                        call.respond(result.code, result.data)
                    }
                }
            }
        }
    }
    route(path = "/getUserInfo"){
        get {
            val userId = call.parameters["userId"]?.toLongOrNull()
            if (userId == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    message = "Invalid credentials!"
                )
            } else {
                when(val result = repository.getUserInfo(userId)){
                    is Response.Success -> {
                        call.respond(
                            result.code, result.data
                        )
                    }
                    is Response.Error -> {
                        call.respond(result.code, result.data)
                    }
                }
            }
        }
    }
    route(path = "/updateUserInfo"){
        post {
            val params = call.receiveNullable<UserInfo>()
            if (params == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    message = "Invalid credentials!"
                )
            } else {
                when(val result = repository.updateUser(params)){
                    is Response.Success -> {
                        call.respond(
                            result.code, result.data
                        )
                    }
                    is Response.Error -> {
                        call.respond(result.code, result.data)
                    }
                }
            }
        }
    }

}
















