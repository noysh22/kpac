package io.proj.kpac

import io.github.charlietap.chasm.embedding.invoke
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.getOrNull
import io.github.charlietap.chasm.runtime.value.ExecutionValue
import io.github.charlietap.chasm.runtime.value.NumberValue.I32
import io.proj.kpac.wasm.WasmLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import org.junit.jupiter.api.assertNotNull
import java.io.File

class WasmTest {

    companion object {
        private const val WASM_PATH = "src/test/wasm/math.wasm"
    }

    @Test
    fun `test wasm file exists`() {
        // Get the path to the WASM file from Bazel runfiles
        val wasmFile = File(WASM_PATH)

        // If running under Bazel, the file should be in the runfiles
        val runfilesWasmFile = File(System.getProperty("user.dir") + "/" + WASM_PATH)

        // Try both locations
        val actualWasmFile = if (wasmFile.exists()) wasmFile else runfilesWasmFile

        assertTrue(actualWasmFile.exists(), "WASM file should exist at: ${actualWasmFile.absolutePath}")
        assertTrue(actualWasmFile.length() > 0, "WASM file should not be empty")

        println("WASM file found at: ${actualWasmFile.absolutePath}")
        println("WASM file size: ${actualWasmFile.length()} bytes")
    }

    @Test
    fun `test wasm file content`() {
        val wasmFile = File(WASM_PATH)
        val runfilesWasmFile = File(System.getProperty("user.dir") + "/" + WASM_PATH)
        val actualWasmFile = if (wasmFile.exists()) wasmFile else runfilesWasmFile

        assertTrue(actualWasmFile.exists(), "WASM file should exist")

        val wasmBytes = actualWasmFile.readBytes()

        // Check WASM magic number (0x00 0x61 0x73 0x6d)
        assertTrue(wasmBytes.size >= 4, "WASM file should be at least 4 bytes")
        assertTrue(wasmBytes[0] == 0x00.toByte(), "WASM magic byte 0")
        assertTrue(wasmBytes[1] == 0x61.toByte(), "WASM magic byte 1 ('a')")
        assertTrue(wasmBytes[2] == 0x73.toByte(), "WASM magic byte 2 ('s')")
        assertTrue(wasmBytes[3] == 0x6d.toByte(), "WASM magic byte 3 ('m')")

        println("WASM file is valid with magic number: ${wasmBytes.take(4).map { "%02x".format(it) }.joinToString(" ")}")
    }

    @Test
    fun `test loadModule`() {
        val (module, instance) = WasmLoader().loadModule(WASM_PATH)
            ?: throw IllegalStateException("Failed to load WASM module")

        assertNotNull(instance, "Failed to load WASM module")
        assertNotNull(module, "Failed to load WASM module")
    }

    @Test
    fun `test invoking a wasm function`() {
        val wasmLoader = WasmLoader()
        val (_, instance) = wasmLoader.loadModule(WASM_PATH)
            ?: throw IllegalStateException("Failed to load WASM module")

        val params = listOf(I32(3), I32(5))

        // Call the function with example arguments
        val result = invoke(wasmLoader.store(), instance, "add", params)

        assertInstanceOf<ChasmResult.Success<ExecutionValue>>(result)
        assertEquals(I32(8), result.getOrNull()?.firstOrNull())
    }
}
