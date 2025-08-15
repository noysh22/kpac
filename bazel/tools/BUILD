# OPA binary wrapper with platform selection
alias(
    name = "opa",
    actual = select({
        "@platforms//os:macos": "@opa_binary_darwin_arm64//file",
        "@platforms//os:linux": "@opa_binary_linux_amd64//file",
        "//conditions:default": "@opa_binary_linux_amd64//file",
    }),
    visibility = ["//visibility:public"],
)
