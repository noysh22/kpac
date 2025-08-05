"""Rules for compiling Rego files to WASM."""

def _rego_to_wasm_impl(ctx):
    """Implementation of rego_to_wasm rule."""
    input_file = ctx.file.src
    wasm_output = ctx.actions.declare_file(ctx.attr.name + ".wasm")
    bundle_output = ctx.actions.declare_file(ctx.attr.name + "_bundle.tar.gz")
    
    ctx.actions.run(
        inputs = [input_file],
        outputs = [wasm_output, bundle_output],
        executable = ctx.executable._compile_script,
        arguments = [ctx.executable._opa.path, input_file.path, wasm_output.path, bundle_output.path],
        tools = [ctx.executable._opa],
        mnemonic = "RegoToWasm",
        progress_message = "Compiling Rego to WASM: %s" % input_file.short_path,
    )
    
    return [DefaultInfo(files = depset([wasm_output, bundle_output]))]

rego_to_wasm = rule(
    implementation = _rego_to_wasm_impl,
    attrs = {
        "src": attr.label(
            allow_single_file = [".rego"],
            mandatory = True,
            doc = "The Rego source file to compile.",
        ),
        "_compile_script": attr.label(
            default = "//scripts:rego_to_wasm",
            executable = True,
            cfg = "exec",
        ),
        "_opa": attr.label(
            default = Label("//bazel/tools:opa"),
            executable = True,
            cfg = "exec",
        ),
    },
    doc = "Compiles a Rego file to WASM and keeps both WASM and bundle files.",
)
