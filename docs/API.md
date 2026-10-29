# NativeRuntimeContracts API Reference

`NativeRuntimeContracts` acts as the root Layer 0 Universal Foundation Contract library.

---

## 1. Primary Entry Point: `NativeRuntimeContracts`

Main singleton orchestrator and state engine.

### Methods

#### `static NativeRuntimeContracts getInstance()`
Returns the singleton instance.

#### `NativeRuntimeContractsResult<RuntimeState> init(Context context, NativeRuntimeContractsConfig config)`
Initializes the library with application context and configuration.
Transitions state from `UNINITIALIZED -> INITIALIZING -> READY`.

#### `NativeRuntimeContractsResult<RuntimeState> suspend()`
Suspends the runtime (`READY -> SUSPENDED`).

#### `NativeRuntimeContractsResult<RuntimeState> resume()`
Resumes the runtime (`SUSPENDED -> READY`).

#### `NativeRuntimeContractsResult<RuntimeState> terminate()`
Terminates the runtime (`TERMINATED`).

#### `boolean isInitialized()`
Returns `true` if the runtime is initialized and currently in `READY` state.

#### `RuntimeState getState()`
Returns the active state enum.

#### `NativeRuntimeContractsConfig getConfig()`
Returns the active configuration object.

#### `void addListener(INativeRuntimeContractsListener listener)`
Registers a listener for lifecycle events and errors.

#### `void removeListener(INativeRuntimeContractsListener listener)`
Unregisters a listener.

#### `void registerApiHandler(IApiHandler handler)`
Registers an RPC command handler for path/action routing.

#### `NativeApiResponse dispatchApiRequest(NativeApiRequest request)`
Dispatches an RPC request through matching handlers or returns error envelope.

#### `void registerService(Class<T> serviceClass, T instance)`
Registers a service in the local service registry.

#### `T getService(Class<T> serviceClass)`
Retrieves a service from the registry.

#### `void log(LogLevel level, String tag, String message)`
Dispatches a log event.

#### `static String encodeBase64(byte[] data)`
Encodes byte array to Base64 string.

#### `static byte[] decodeBase64(String base64)`
Decodes Base64 string to byte array.

#### `void release()`
Releases all resources and resets singleton state to `TERMINATED` / uninitialized.

---

## 2. Configuration: `NativeRuntimeContractsConfig`

| Method | Default | Description |
|--------|---------|-------------|
| `debugEnabled(boolean)` | `false` | Enable debug logging |
| `tag(String)` | `"NativeRuntimeContracts"` | Log tag |
| `timeout(int)` | `30000` | Timeout in milliseconds |
| `build()` | — | Create configuration |

---

## 3. Results & State

### `NativeRuntimeContractsResult<T>`
- `isSuccess()`: `boolean`
- `getData()`: `T`
- `getErrorMessage()`: `String`
- `getErrorCode()`: `int`

### `RuntimeState`
- `UNINITIALIZED`
- `INITIALIZING`
- `READY`
- `SUSPENDED`
- `TERMINATED`
- `isTerminal()`: Returns true if state is final.
- `isOperational()`: Returns true if state is `READY`.

---

## 4. Universal RPC & API Models

### `NativeApiRequest`
- `getRequestId()`: `String`
- `getPath()`: `String`
- `getAction()`: `String`
- `getParameters()`: `Map<String, Object>`
- `getStringParam(String key, String defaultValue)`: `String`
- `getBooleanParam(String key, boolean defaultValue)`: `boolean`
- `getTimestamp()`: `long`

### `NativeApiResponse`
- `success(String requestId, Map<String, Object> data)`
- `error(String requestId, int statusCode, NativeApiError error)`
- `isSuccess()`: `boolean`
- `getStatusCode()`: `int`
- `getData()`: `Map<String, Object>`
- `getError()`: `NativeApiError`

### `NativeApiError`
- `getCode()`: `String`
- `getMessage()`: `String`
- `getDetails()`: `String`

---

## 5. Telemetry & Subsystem DTOs

- `AnalyticsEvent`: Name, parameters map, timestamp.
- `PerformanceMetric`: Metric name, value (double), unit (String), timestamp.
- `BatteryStatusDTO`: Battery level (%), isCharging, pluggedType, temperature.
- `DeviceInfoDTO`: Manufacturer, model, osVersion, sdkInt, deviceUuid, totalMemory.
- `ToolbarConfig`: Title, subtitle, background color, title color, action items list.
- `DialogConfig`: Title, message, positive button, negative button, cancelable flag.
- `ExecutionContext`: Session ID, correlation ID, custom attributes map.
- `WebSourceType`: `LOCAL_ASSET`, `LOCAL_SERVER`, `REMOTE_URL`.

