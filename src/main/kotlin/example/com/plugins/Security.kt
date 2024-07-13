package example.com.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.dao.user.UserDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

private val jwtAudience = System.getenv("jwtAudience")
private val jwtIssuer = System.getenv("jwtIssuer")
private val jwtSecret = System.getenv("jwtSecret")

private const val CLAIM = "email"

fun Application.configureSecurity() {
    val userDao by inject<UserDao>()

    authentication {
        jwt {

            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(CLAIM).asString() != null) {
                    val userExists = userDao.findByEmail(email = credential.payload.getClaim(CLAIM).asString()) != null
                    val isValidAudience = credential.payload.audience.contains(jwtAudience)
                    if (userExists && isValidAudience) {
                        JWTPrincipal(payload = credential.payload)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "Token is not valid or has expired"
                    )
                )
            }
        }
    }
}

fun generateToken(email: String): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM, email)
        //.withExpiresAt()
        .sign(Algorithm.HMAC256(jwtSecret))
}




@Serializable
data class AuthResponse(
    val data : AuthResponseData? = null,
    val errorMessage:  String? = null
)

@Serializable
data class AuthResponseData(
    val id: Long,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
)




