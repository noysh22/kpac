package io.proj.kpac.wasm

import io.github.charlietap.chasm.embedding.error.ChasmError.DecodeError
import io.github.charlietap.chasm.embedding.error.ChasmError.ExecutionError
import io.github.charlietap.chasm.embedding.instance
import io.github.charlietap.chasm.embedding.module
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.Import
import io.github.charlietap.chasm.embedding.shapes.Instance
import io.github.charlietap.chasm.embedding.shapes.Module
import io.github.charlietap.chasm.embedding.shapes.Store
import io.github.charlietap.chasm.embedding.store
import org.slf4j.LoggerFactory
import java.io.File

class WasmLoader(private val store: Store = store()) {

    fun store(): Store = store

    fun loadModule(wasmFilePath: String, imports: List<Import> = emptyList()): Pair<Module, Instance>? {
        return try {
            val wasmFile = File(wasmFilePath)
            if (!wasmFile.exists()) {
                log.error("WASM file not found: $wasmFilePath")
                return null
            }

            val wasmBytes = wasmFile.readBytes()
            log.info("Loading WASM module from: $wasmFilePath (${wasmBytes.size} bytes)")

            return loadModule(wasmBytes, imports)
        } catch (e: Exception) {
            log.error("Exception while loading WASM module: ${e.message}", e)
            null
        }
    }

    fun loadModule(wasmBytes: ByteArray, imports: List<Import> = emptyList()): Pair<Module, Instance>? {
        return try {
            // Parse the WASM module
            val module = when (val moduleResult: ChasmResult<Module, DecodeError> = module(wasmBytes)) {
                is ChasmResult.Error -> {
                    log.error("Failed to parse WASM module: ${moduleResult.error}")
                    return null
                }
                is ChasmResult.Success -> {
                    log.info("Successfully parsed WASM module")
                    moduleResult.result
                }
            }

            // Instantiate the module
            val instance = when (val instanceResult: ChasmResult<Instance, ExecutionError> = instance(store, module, imports)) {
                is ChasmResult.Error -> {
                    log.error("Failed to instantiate WASM module: ${instanceResult.error}")
                    return null
                }
                is ChasmResult.Success -> {
                    log.info("Successfully instantiated WASM module")
                    instanceResult.result
                }
            }

            // You can now call exported functions from the WASM module
            // Example: wasmInstance.exports.find { it.name == "your_function_name" }

            return module to instance
        } catch (e: Exception) {
            log.error("Exception while loading WASM module: ${e.message}", e)
            null
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(WasmLoader::class.java)
    }
}
