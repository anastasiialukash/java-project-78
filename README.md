# Data Validator

**Data Validator** is a lightweight Java library for runtime validation of data structures.


## Features

- **Built-in schemas**: `string()`, `number()`, `map()`.
- **Fluent validators**: chain methods like `required()`, `minLength()`, `positive()`, `sizeof()`, and more.
- **Zero external dependencies**: only requires the JDK.

## Getting Started

### Prerequisites

- Java 11+
- Gradle

### Clone & Build

```bash
git clone https://github.com/anastasiialukash/java-project-78.git
cd app
```

### Examples
```java
Validator v = new Validator();
boolean ok = v.string()
.required()
.minLength(3)
.isValid("Hello");  // returns true

boolean ok = v.number()
        .required()
        .positive()
        .isValid(42); // returns true

```
###

### Hexlet tests and linter status:
[![Actions Status](https://github.com/anastasiialukash/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/anastasiialukash/java-project-78/actions)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=anastasiialukash_java-project-78&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=anastasiialukash_java-project-78)