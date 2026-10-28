# NativeRuntimeContracts

Universal foundation interfaces, DTOs, encoding standards, and runtime state machine for NativeRuntime Android ecosystem.

[![JitPack](https://jitpack.io/v/infoadmstudio2020-cyber/NativeRuntimeContracts.svg)](https://jitpack.io/#infoadmstudio2020-cyber/NativeRuntimeContracts)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache-2.0-blue.svg)](LICENSE)

## Installation

### Step 1 — Add JitPack to your root build.gradle

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2 — Add the dependency

```gradle
dependencies {
    implementation 'com.github.infoadmstudio2020-cyber:NativeRuntimeContracts:1.0.0'
}
```

## Usage

```java
NativeRuntimeContractsConfig config = new NativeRuntimeContractsConfig.Builder()
    .debugEnabled(BuildConfig.DEBUG)
    .build();

NativeRuntimeContracts.getInstance().init(context, config);
```

## API

| Method | Description |
|--------|-------------|
| `init(context, config)` | Initialize the library |
| `isInitialized()` | Check initialization state |
| `getConfig()` | Get current configuration |
| `release()` | Release all resources |

## Requirements

- Android API 21+
- Java 17+

## Changelog

See [CHANGELOG.md](CHANGELOG.md)

## License

```
Copyright 2026 infoadmstudio2020-cyber

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
