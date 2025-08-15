package io.proj.kpac.opa

import io.github.charlietap.chasm.embedding.dsl.functionImport
import io.github.charlietap.chasm.embedding.dsl.imports
import io.github.charlietap.chasm.embedding.shapes.Import
import io.github.charlietap.chasm.embedding.shapes.Instance
import io.github.charlietap.chasm.embedding.shapes.Module
import io.github.charlietap.chasm.embedding.shapes.Store
import io.proj.kpac.wasm.WasmLoader

object OpaFunctions {
    fun opaAbort(store: Store) = functionImport(store) {
    }
}

fun opaImports(store: Store, moduleName: String): List<Import> = imports(store) {
    memory {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_MEMORY
        type {
            limits {
                min = 5U
            }
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_ABORT
        type {
            params { i32() }
            results { }
        }
        reference {
            emptyList()
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_BUILTIN0
        type {
            params {
                i32()
                i32()
            }
            results { i32() }
        }
        reference {
            emptyList()
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_BUILTIN1
        type {
            params {
                i32()
                i32()
                i32()
            }
            results { i32() }
        }
        reference {
            emptyList()
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_BUILTIN2
        type {
            params {
                i32()
                i32()
                i32()
                i32()
            }
            results { i32() }
        }
        reference {
            emptyList()
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_BUILTIN3
        type {
            params {
                i32()
                i32()
                i32()
                i32()
                i32()
            }
            results { i32() }
        }
        reference {
            emptyList()
        }
    }
    function {
        this.moduleName = moduleName
        entityName = OpaConstants.OPA_BUILTIN4
        type {
            params {
                i32()
                i32()
                i32()
                i32()
                i32()
                i32()
            }
            results { i32() }
        }
        reference {
            emptyList()
        }
    }
}

class OpaModule(
    val module: Module,
    val instance: Instance,
    val moduleName: String = OpaConstants.OPA_MODULE_NAME,
) {
    companion object {
        fun fromFile(filePath: String): OpaModule? {
            // Load the module from the file
            val wasmLoader = WasmLoader()
            val (module, instance) = wasmLoader.loadModule(
                filePath,
                imports = opaImports(wasmLoader.store(), OpaConstants.OPA_MODULE_NAME),
            ) ?: return null

            // Return an OpaModule instance
            return OpaModule(module, instance)
        }

        fun fromBytes(policyBytes: ByteArray): OpaModule? {
            val wasmLoader = WasmLoader()
            val (module, instance) = wasmLoader.loadModule(
                policyBytes,
                imports = opaImports(wasmLoader.store(), OpaConstants.OPA_MODULE_NAME),
            ) ?: return null

            return OpaModule(module, instance)
        }
    }
}
