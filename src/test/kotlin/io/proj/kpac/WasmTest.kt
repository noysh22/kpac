package io.proj.kpac

import io.github.charlietap.chasm.embedding.invoke
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.getOrNull
import io.github.charlietap.chasm.runtime.value.ExecutionValue
import io.github.charlietap.chasm.runtime.value.NumberValue.I32
import io.kotest.assertions.asClue
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.proj.kpac.wasm.WasmLoader
import org.junit.jupiter.api.Test
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

        "WASM file should exist at: ${actualWasmFile.absolutePath}".asClue {
            actualWasmFile.exists() shouldBe true
        }
        "WASM file should not be empty".asClue {
            actualWasmFile.length() shouldBeGreaterThan 0L
        }

        println("WASM file found at: ${actualWasmFile.absolutePath}")
        println("WASM file size: ${actualWasmFile.length()} bytes")
    }

    @Test
    fun `test wasm file content`() {
        val wasmFile = File(WASM_PATH)
        val runfilesWasmFile = File(System.getProperty("user.dir") + "/" + WASM_PATH)
        val actualWasmFile = if (wasmFile.exists()) wasmFile else runfilesWasmFile

        "WASM file should exist".asClue { actualWasmFile.exists() shouldBe true }

        val wasmBytes = actualWasmFile.readBytes()

        // Check WASM magic number (0x00 0x61 0x73 0x6d)
        "WASM file should be at least 4 bytes".asClue { wasmBytes.size shouldBeGreaterThan 3 }
        "WASM magic byte 0".asClue { wasmBytes[0] shouldBe 0x00.toByte() }
        "WASM magic byte 1 ('a')".asClue { wasmBytes[1] shouldBe 0x61.toByte() }
        "WASM magic byte 2 ('s')".asClue { wasmBytes[2] shouldBe 0x73.toByte() }
        "WASM magic byte 3 ('m')".asClue { wasmBytes[3] shouldBe 0x6d.toByte() }

        println("WASM file is valid with magic number: ${wasmBytes.take(4).map { "%02x".format(it) }.joinToString(" ")}")
    }

    @Test
    fun `test loadModule`() {
        val (module, instance) = WasmLoader().loadModule(WASM_PATH)
            ?: throw IllegalStateException("Failed to load WASM module")

        "Failed to load WASM instance".asClue { instance.shouldNotBeNull() }
        "Failed to load WASM module".asClue { module.shouldNotBeNull() }
    }

    @Test
    fun `test invoking a wasm function`() {
        val wasmLoader = WasmLoader()
        val (_, instance) = wasmLoader.loadModule(WASM_PATH)
            ?: throw IllegalStateException("Failed to load WASM module")

        val params = listOf(I32(3), I32(5))

        // Call the function with example arguments
        val result = invoke(wasmLoader.store(), instance, "add", params)

        "Result should be successful".asClue {
            result.shouldBeInstanceOf<ChasmResult.Success<ExecutionValue>>()
        }
        "Function result should equal I32(8)".asClue {
            result.getOrNull()?.firstOrNull() shouldBe I32(8)
        }
    }
}
