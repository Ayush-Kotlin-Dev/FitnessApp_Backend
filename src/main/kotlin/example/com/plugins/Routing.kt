package example.com.plugins

import example.com.route.authRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/") {
            get {
                call.respondText("Hello Users this is Ayush!")
            }
        }
        authRouting()
    }
}
