package io.proj.kpac.opa

typealias OpaAddr = Int
typealias OpaError = Int

interface OpaApi {

    // Context management
    fun opaEvalCtxNew(): OpaAddr
    fun opaEvalCtxSetInput(ctx: OpaAddr, input: OpaAddr)
    fun opaEvalCtxSetData(ctx: OpaAddr, data: OpaAddr)
    fun opaEvalCtxSetEntrypoint(ctx: OpaAddr, entrypoint: OpaAddr)
    fun opaEvalCtxGetResult(ctx: OpaAddr): OpaAddr

    // Memory management
    fun opaMalloc(size: Int): OpaAddr
    fun opaFree(ptr: OpaAddr)
    fun opaHeapPtrGet(): OpaAddr
    fun opaHeapPtrSet(ptr: OpaAddr)
    fun opaHeapBlocksStash()
    fun opaHeapStashClear()
    fun opaHeapBlocksRestore()

    // Value operations
    fun opaValueParse(ptr: OpaAddr, size: Int): OpaAddr
    fun opaValueDump(value: OpaAddr): OpaAddr
    fun opaValueFree(value: OpaAddr)
    fun opaValueAddPath(value: OpaAddr, path: OpaAddr, pathSize: OpaAddr): OpaError
    fun opaValueRemovePath(value: OpaAddr, path: OpaAddr): OpaError

    // JSON operations
    fun opaJsonParse(ptr: OpaAddr, size: OpaAddr): OpaAddr
    fun opaJsonDump(value: OpaAddr): OpaAddr

    // Evaluation
    fun opaEval(ctx: OpaAddr, entrypoint: OpaAddr, input: OpaAddr, data: OpaAddr, heapPtr: OpaAddr, formatAddr: OpaAddr, resultAddr: OpaAddr): OpaAddr
    fun eval(ctx: OpaAddr): OpaError

    // Metadata
    fun builtins(): OpaAddr
    fun entrypoints(): OpaAddr
}
