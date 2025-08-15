package io.github.noysh22.kpac

import io.javalin.Javalin
import io.javalin.http.Context
import org.slf4j.LoggerFactory

object Controller {
    fun get(ctx: Context) {
        ctx.result("Welcome")
    }

    fun post(ctx: Context) {
        ctx.json(ctx.body())
    }
}

class Server(val port: Int) {

    private val app = Javalin.create()
        .apply { configureRoutes(this) }

    private fun configureRoutes(app: Javalin) {
        app.get("/", Controller::get)
        app.post("/echo", Controller::post)
    }

    fun start() {
        app.start(port)
        log.info("Started server on port $port")
    }

    fun stop() {
        app.stop()
        log.info("Stopped http server")
    }

    companion object {
        private val log = LoggerFactory.getLogger(Server::class.java)
    }
}

fun main() {
    println("Starting KPAC with WASM support...")

    // Initialize WASM loader
//    val wasmLoader = WasmLoader()
//
//    // Try to load a WASM module if specified
//    val wasmPath = System.getenv("WASM_MODULE_PATH")
//    if (wasmPath != null) {
//        println("Attempting to load WASM module from: $wasmPath")
//        val loaded = wasmLoader.loadModule(wasmPath)
//        if (loaded) {
//            println("WASM module loaded successfully!")
//        } else {
//            println("Failed to load WASM module, continuing without it...")
//        }
//    } else {
//        println("No WASM_MODULE_PATH environment variable set, skipping WASM loading")
//        println("To load a WASM module, set WASM_MODULE_PATH to your .wasm file path")
//    }

//    val port = System.getenv("PORT")?.toInt() ?: 7070
//
//    val server = Server(port)
//
//    Runtime.getRuntime().addShutdownHook(
//        Thread {
//            println("Shutting down")
//            server.stop()
//        }
//    )
//    server.start()
}
