package io.proj.kpac.opa

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import java.io.File
import java.io.InputStream
import java.util.zip.GZIPInputStream

class OpaBundle(
    val policy: OpaModule,
    val data: String,
) {
    companion object {
        fun fromTarGz(tarGzPath: String): OpaBundle = extractBundle(File(tarGzPath).inputStream())

        fun fromInputStream(inputStream: InputStream): OpaBundle = extractBundle(inputStream)

        private fun extractBundle(inputStream: InputStream): OpaBundle {
            GZIPInputStream(inputStream).use { gzis ->
                TarArchiveInputStream(gzis).use { tis ->
                    var entry: TarArchiveEntry? = tis.nextEntry
                    var policyBytes: ByteArray? = null
                    var data: String? = null

                    while (entry != null) {
                        when {
                            entry.name.endsWith(".wasm") -> {
                                policyBytes = tis.readBytes()
                            }
                            entry.name.endsWith("data.json") ||
                                entry.name.endsWith(".json") -> {
                                data = tis.readBytes().toString(Charsets.UTF_8)
                            }
                            // Handle policy files if needed
                            entry.name.endsWith(".rego") -> {
                                // Process Rego files if needed
                            }
                        }
                        entry = tis.nextEntry
                    }

                    return if (policyBytes != null && data != null) {
                        OpaModule.fromBytes(policyBytes)?.let {
                            OpaBundle(it, data)
                        } ?: throw Exception("Failed to create OpaModule from bytes")
                    } else {
                        throw IllegalArgumentException("Invalid OPA bundle: missing policy or data")
                    }
                }
            }
        }
    }
}
