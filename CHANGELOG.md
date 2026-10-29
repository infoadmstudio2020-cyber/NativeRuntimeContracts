# Changelog

All notable changes to NativeRuntimeContracts are documented here.
Format based on [Keep a Changelog](https://keepachangelog.com).

## [1.0.1] — 2026-10-29

### Added
- **Layer 0 Universal Foundation Contracts**: Implemented all 23 key domain interfaces and DTOs:
  - `analytics`: `AnalyticsEvent`, `IAnalyticsProvider`, `PerformanceMetric`
  - `api`: `IApiCallback`, `IApiHandler`, `NativeApiError`, `NativeApiRequest`, `NativeApiResponse`
  - `common`: `ExecutionContext`, `IDisposable`, `RuntimeState`
  - `logger`: `ILogger`, `LogEntry`, `LogLevel`
  - `platform`: `BatteryStatusDTO`, `DeviceInfoDTO`
  - `runtime`: `IWebSource`, `WebSourceType`
  - `service`: `IService`, `IServiceRegistry`
  - `theme`: `IThemeConfig`
  - `ui`: `DialogConfig`, `ToolbarConfig`
- **Core Engine & State Machine**:
  - Thread-safe state machine transitions (`UNINITIALIZED -> INITIALIZING -> READY -> SUSPENDED -> TERMINATED`).
  - RPC request dispatching engine with handler registry and fallback error envelopes.
  - In-memory service registry integration.
  - Pure JVM Base64 encoding/decoding helper utilities.
- **R8 / ProGuard Rules**:
  - Comprehensive `consumer-rules.pro` keeping contracts, public interfaces, Serializable DTOs, and builders.
- **Automated Testing**:
  - Comprehensive JVM unit test suite (`NativeRuntimeContractsTest`) covering state machine, API dispatch, DTOs, LogLevel, Analytics, and Config builders.
- **Interactive Companion Demo App**:
  - Live test bench in `MainActivity` with real-time state transitions, RPC execution, analytics logging, and DTO telemetry display.

