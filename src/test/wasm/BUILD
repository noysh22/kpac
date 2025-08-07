load("@bazel_skylib//rules:run_binary.bzl", "run_binary")

# Rule to compile WAT to WASM using wat2wasm
genrule(
    name = "math_wasm",
    srcs = ["math.wat"],
    outs = ["math.wasm"],
    cmd = "wat2wasm $< -o $@",
    visibility = ["//visibility:public"],
)

# Make the WASM file available as a data dependency
filegroup(
    name = "math_wasm_data",
    srcs = [":math_wasm"],
    visibility = ["//visibility:public"],
)
