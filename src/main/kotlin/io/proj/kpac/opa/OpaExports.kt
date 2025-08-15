package io.proj.kpac.opa

class OpaExports : OpaApi {
    override fun opaEvalCtxNew(): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaEvalCtxSetInput(ctx: OpaAddr, input: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaEvalCtxSetData(ctx: OpaAddr, data: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaEvalCtxSetEntrypoint(ctx: OpaAddr, entrypoint: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaEvalCtxGetResult(ctx: OpaAddr): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaMalloc(size: Int): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaFree(ptr: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaHeapPtrGet(): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaHeapPtrSet(ptr: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaHeapBlocksStash() {
        TODO("Not yet implemented")
    }

    override fun opaHeapStashClear() {
        TODO("Not yet implemented")
    }

    override fun opaHeapBlocksRestore() {
        TODO("Not yet implemented")
    }

    override fun opaValueParse(ptr: OpaAddr, size: Int): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaValueDump(value: OpaAddr): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaValueFree(value: OpaAddr) {
        TODO("Not yet implemented")
    }

    override fun opaValueAddPath(value: OpaAddr, path: OpaAddr, pathSize: OpaAddr): OpaError {
        TODO("Not yet implemented")
    }

    override fun opaValueRemovePath(value: OpaAddr, path: OpaAddr): OpaError {
        TODO("Not yet implemented")
    }

    override fun opaJsonParse(ptr: OpaAddr, size: OpaAddr): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaJsonDump(value: OpaAddr): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun opaEval(ctx: OpaAddr, entrypoint: OpaAddr, input: OpaAddr, data: OpaAddr, heapPtr: OpaAddr, formatAddr: OpaAddr, resultAddr: OpaAddr): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun eval(ctx: OpaAddr): OpaError {
        TODO("Not yet implemented")
    }

    override fun builtins(): OpaAddr {
        TODO("Not yet implemented")
    }

    override fun entrypoints(): OpaAddr {
        TODO("Not yet implemented")
    }
}
