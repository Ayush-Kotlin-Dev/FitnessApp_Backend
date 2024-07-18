package example.com.route


import example.com.model.SignInParams
import example.com.model.SignUpParams
import example.com.repository.AuthRepository
import example.com.plugins.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting(){
    val repository by inject<AuthRepository>()

    route(path = "/signup"){
        post {

            val params = call.receiveNullable<SignUpParams>()

            if (params == null){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )
                return@post
            }

            val result = repository.signUp(signUpParams = params)
            call.respond(
                status = result.code,
                message = result.data
            )

        }
    }

    route(path = "/login"){
        post {

            val params = call.receiveNullable<SignInParams>()

            if (params == null){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )
                return@post
            }

            val result = repository.signIn(signInParams  = params)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }

}
















