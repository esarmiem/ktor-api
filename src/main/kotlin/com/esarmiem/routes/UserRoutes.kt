package com.esarmiem.routes
import com.esarmiem.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val users = mutableListOf(
    User(1, "elder", 20, "elder@j.com"),
    User(2, "maria", 21, "maria@j.com"),
)

fun Route.userRouting() {
    route("/users") {
        get {
            if (users.isNotEmpty()) {
                call.respond(users)
            } else {
                call.respondText("No hay usuarios bro", status = HttpStatusCode.OK)
            }
        }
        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                "ID no encontrado",
                status = HttpStatusCode.BadRequest
            )
            val user = users.find { it.id == id } ?: return@get call.respondText(
                "No existe el usuario con id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(user)
        }
        post {
            val user = call.receive<User>()
            users.add(user)
            call.respondText("Usuario agregado", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respondText(
                "ID no encontrado",
                status = HttpStatusCode.BadRequest
            )
            if (users.removeIf { it.id == id }) {
                call.respondText("Usuario eliminado", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("No existe el usuario con id $id", status = HttpStatusCode.NotFound)
            }
        }
    }
}