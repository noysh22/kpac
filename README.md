# Kotlin Policy as Code (KPAC)

This repository contains Kotlin code for practicing **Policy as Code** using **Open Policy Agent (OPA)** and **WebAssembly (WASM)**.

## Overview

This project demonstrates how to integrate policy evaluation into Kotlin applications using OPA's WASM capabilities. Policy as Code allows you to define, version, and enforce policies programmatically, making compliance and governance part of your development workflow.

## Technologies Used

- **Kotlin**: Primary programming language
- **Bazel 8**: Build system using Bazel modules
- **Open Policy Agent (OPA)**: Policy engine
- **WebAssembly (WASM)**: Runtime for compiled OPA policies

## Features

- Policy evaluation in Kotlin applications
- WASM-compiled OPA policies for performance
- Bazel-based build system for reproducible builds using modules
- Examples of common policy patterns

## Prerequisites

- Java 11 or higher
- Bazel 8 or higher
- OPA CLI (for policy development and testing)

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/kpac.git
cd kpac
```

### 2. Build the project
```bash
bazel build //...
```

### 3. Run tests
```bash
bazel test //...
```

## Project Structure

```
├── src/                    # Kotlin source code
├── policies/              # OPA policy files (.rego)
├── MODULE.bazel           # Bazel module configuration
└── README.md           # This file
```

## Usage

[Add specific usage examples here based on your implementation]

## Policy Development

1. Write policies in Rego language in the `policies/` directory
2. Compile policies to WASM using OPA
3. Load and evaluate policies in your Kotlin application

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

[Add your license here]

## Resources

- [Open Policy Agent Documentation](https://www.openpolicyagent.org/docs/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Bazel Documentation](https://bazel.build/docs)
