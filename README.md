# NativeRuntimeContracts

Universal foundation interfaces, DTOs, encoding standards, and runtime state machine for the NativeRuntime Android ecosystem (Layer 0).

[![JitPack](https://jitpack.io/v/infoadmstudio2020-cyber/NativeRuntimeContracts.svg)](https://jitpack.io/#infoadmstudio2020-cyber/NativeRuntimeContracts)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache-2.0-blue.svg)](LICENSE)
[![Architecture](https://img.shields.io/badge/Layer-0%20Universal%20Foundation-blueviolet.svg)](#architecture)

---

## Overview

`NativeRuntimeContracts` acts as the root zero-dependency contract layer for all downstream engines in the NativeRuntime Android SDK ecosystem (`NativeServiceSDK`, `NativeLoggerSDK`, `NativeRouterSDK`, `NativeWebBridgeSDK`, `NativeRuntimeSDK`, etc.).

### Key Features
- **Deterministic State Machine**: `UNINITIALIZED -> INITIALIZING -> READY -> SUSPENDED -> TERMINATED`.
- **Standardized Result Envelope**: `NativeRuntimeContractsResult<T>` with zero unhandled runtime crashes.
- **Universal RPC Protocol**: `NativeApiRequest`, `NativeApiResponse`, `NativeApiError` with handler routing.
- **Complete Foundation Contracts (23 Interfaces & DTOs)**:
  - `analytics`: `AnalyticsEvent`, `IAnalyticsProvider`, `PerformanceMetric`
  - `api`: `IApiCallback`, `IApiHandler`, `NativeApiError`, `NativeApiRequest`, `NativeApiResponse`
  - `common`: `ExecutionContext`, `IDisposable`, `RuntimeState`
  - `logger`: `ILogger`, `LogEntry`, `LogLevel`
  - `platform`: `BatteryStatusDTO`, `DeviceInfoDTO`
  - `runtime`: `IWebSource`, `WebSourceType`
  - `service`: `IService`, `IServiceRegistry`
  - `theme`: `IThemeConfig`
  - `ui`: `DialogConfig`, `ToolbarConfig`
- **Consumer ProGuard Rules**: Zero-config R8 preservation of contracts, serializable DTOs, and builders.

---

## Installation

### Step 1 — Add JitPack to your project

In `settings.gradle`:
```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2 — Add the dependency

In your module's `build.gradle`:
```gradle
dependencies {
    implementation 'com.github.infoadmstudio2020-cyber:NativeRuntimeContracts:1.0.1'
}
```

---

## Quick Start

### 1. Initialize Runtime
```java
NativeRuntimeContractsConfig config = new NativeRuntimeContractsConfig.Builder()
    .debugEnabled(BuildConfig.DEBUG)
    .tag("MyApp")
    .timeout(30000)
    .build();

NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
NativeRuntimeContractsResult<RuntimeState> result = lib.init(context, config);

if (result.isSuccess()) {
    // Runtime is in READY state
}
```

### 2. Register and Dispatch API Request
```java
// Register handler
lib.registerApiHandler(new IApiHandler() {
    @NonNull @Override public String getPath() { return "device"; }
    @NonNull @Override public String getAction() { return "info"; }
    @NonNull @Override public NativeApiResponse handleRequest(@NonNull NativeApiRequest req) {
        Map<String, Object> data = new HashMap<>();
        data.put("sdk", Build.VERSION.SDK_INT);
        return NativeApiResponse.success(req.getRequestId(), data);
    }
});

// Dispatch request
NativeApiRequest request = new NativeApiRequest.Builder("device", "info").build();
NativeApiResponse response = lib.dispatchApiRequest(request);
```

### 3. Track Analytics & Metrics
```java
AnalyticsEvent event = new AnalyticsEvent("user_login", Collections.singletonMap("source", "native"));
PerformanceMetric metric = new PerformanceMetric("warm_start", 120.5, "ms");
```

---

## Requirements
- Android API 21+ (Android 5.0 Lollipop or higher)
- Java 17
- Android Gradle Plugin 8.3+

## License
Apache-2.0 License. See [LICENSE](LICENSE) for details.

