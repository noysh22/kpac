package io.proj.kpac

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class WasmTest {

    companion object {
        private const val WASM_PATH = "src/test/wasm/math.wasm"
    }

    @Test
    fun testWasmFileExists() {
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
    fun testWasmFileContent() {
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
}
