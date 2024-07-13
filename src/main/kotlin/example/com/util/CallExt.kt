package example.com.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

suspend fun ApplicationCall.getLongParameter(name: String, isQueryParameter: Boolean = false): Long{
    val parameter = if (isQueryParameter){
        request.queryParameters[name]?.toLongOrNull()
    }else{
        parameters[name]?.toLongOrNull()
    } ?: kotlin.run {
        respond(
            status = HttpStatusCode.BadRequest,
            message = gymResponse(
                success = false,
                message = "Parameter $name is missing or invalid"
            )
        )
        throw BadRequestException("Parameter $name is missing or invalid")
    }
    return parameter
}

@Serializable
data class gymResponse(
    val success: Boolean,
    val message: String
)