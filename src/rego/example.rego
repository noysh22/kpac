package rego

default allow = false

allow if {
    input.method == "GET"
    input.path == "/allowed"
}

allow if {
    input.method == "POST"
    input.user == "admin"
}