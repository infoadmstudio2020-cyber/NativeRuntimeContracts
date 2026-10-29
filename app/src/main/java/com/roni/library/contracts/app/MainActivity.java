package com.roni.library.contracts.app;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.roni.library.contracts.NativeRuntimeContracts;
import com.roni.library.contracts.NativeRuntimeContractsConfig;
import com.roni.library.contracts.NativeRuntimeContractsResult;
import com.roni.library.contracts.analytics.AnalyticsEvent;
import com.roni.library.contracts.analytics.PerformanceMetric;
import com.roni.library.contracts.api.IApiHandler;
import com.roni.library.contracts.api.NativeApiRequest;
import com.roni.library.contracts.api.NativeApiResponse;
import com.roni.library.contracts.common.RuntimeState;
import com.roni.library.contracts.interfaces.INativeRuntimeContractsListener;
import com.roni.library.contracts.logger.LogLevel;
import com.roni.library.contracts.platform.BatteryStatusDTO;
import com.roni.library.contracts.platform.DeviceInfoDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Companion Demonstration Application & Interactive Test Bench for NativeRuntimeContracts.
 *
 * @author infoadmstudio2020-cyber
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTvStatus;
    private TextView mTvLogOutput;
    private final SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvStatus = findViewById(R.id.tv_status);
        mTvLogOutput = findViewById(R.id.tv_log_output);

        Button btnInit = findViewById(R.id.btn_init);
        Button btnSuspend = findViewById(R.id.btn_suspend);
        Button btnResume = findViewById(R.id.btn_resume);
        Button btnRpc = findViewById(R.id.btn_rpc);
        Button btnAnalytics = findViewById(R.id.btn_analytics);
        Button btnDto = findViewById(R.id.btn_dto);
        Button btnRelease = findViewById(R.id.btn_release);
        TextView btnClearLog = findViewById(R.id.btn_clear_log);

        updateStatusDisplay(NativeRuntimeContracts.getInstance().getState());

        NativeRuntimeContracts.getInstance().addListener(new INativeRuntimeContractsListener() {
            @Override
            public void onInitialized() {
                runOnUiThread(() -> {
                    appendLog("ইভেন্ট: লাইব্রেরি সফলভাবে ইনিশিয়ালাইজ হয়েছে (onInitialized)");
                    updateStatusDisplay(NativeRuntimeContracts.getInstance().getState());
                });
            }

            @Override
            public void onError(int errorCode, @NonNull String message) {
                runOnUiThread(() -> {
                    appendLog("ত্রুটি (" + errorCode + "): " + message);
                });
            }

            @Override
            public void onStateChanged(@NonNull RuntimeState oldState, @NonNull RuntimeState newState) {
                runOnUiThread(() -> {
                    appendLog("স্টেট ট্রানজিশন: " + oldState + " -> " + newState);
                    updateStatusDisplay(newState);
                });
            }
        });

        btnInit.setOnClickListener(v -> initRuntime());
        btnSuspend.setOnClickListener(v -> suspendRuntime());
        btnResume.setOnClickListener(v -> resumeRuntime());
        btnRpc.setOnClickListener(v -> testRpcDispatch());
        btnAnalytics.setOnClickListener(v -> testAnalyticsAndMetrics());
        btnDto.setOnClickListener(v -> testDtoTelemetry());
        btnRelease.setOnClickListener(v -> releaseRuntime());
        btnClearLog.setOnClickListener(v -> mTvLogOutput.setText(""));
    }

    private void initRuntime() {
        NativeRuntimeContractsConfig config = new NativeRuntimeContractsConfig.Builder()
                .debugEnabled(true)
                .tag("NativeRuntimeDemo")
                .timeout(30_000)
                .build();

        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
        NativeRuntimeContractsResult<RuntimeState> res = lib.init(getApplicationContext(), config);

        if (res.isSuccess()) {
            appendLog("বুট সফল: স্টেট = " + res.getData());

            // Register a sample API echo handler
            lib.registerApiHandler(new IApiHandler() {
                @NonNull
                @Override
                public String getPath() {
                    return "demo";
                }

                @NonNull
                @Override
                public String getAction() {
                    return "echo";
                }

                @NonNull
                @Override
                public NativeApiResponse handleRequest(@NonNull NativeApiRequest request) {
                    Map<String, Object> out = new HashMap<>();
                    out.put("reply", "Echo: " + request.getStringParam("message", "empty"));
                    out.put("serverTime", System.currentTimeMillis());
                    return NativeApiResponse.success(request.getRequestId(), out);
                }
            });
            appendLog("রেজিস্ট্রেশন: 'demo/echo' RPC হ্যান্ডলার যুক্ত করা হয়েছে");
        } else {
            appendLog("ইনিশিয়ালাইজেশন ব্যর্থ: " + res.getErrorMessage());
        }
    }

    private void suspendRuntime() {
        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
        NativeRuntimeContractsResult<RuntimeState> res = lib.suspend();
        if (res.isSuccess()) {
            appendLog("রানটাইম সাসপেন্ড করা হয়েছে: " + res.getData());
        } else {
            appendLog("সাসপেন্ড ব্যর্থ: " + res.getErrorMessage());
        }
    }

    private void resumeRuntime() {
        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
        NativeRuntimeContractsResult<RuntimeState> res = lib.resume();
        if (res.isSuccess()) {
            appendLog("রানটাইম রিজিউম করা হয়েছে: " + res.getData());
        } else {
            appendLog("রিজিউম ব্যর্থ: " + res.getErrorMessage());
        }
    }

    private void testRpcDispatch() {
        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
        if (!lib.isInitialized()) {
            Toast.makeText(this, "আগে ইনিশিয়ালাইজ করুন!", Toast.LENGTH_SHORT).show();
            appendLog("সতর্কতা: লাইব্রেরি ইনিশিয়ালাইজ করা হয়নি");
            return;
        }

        NativeApiRequest request = new NativeApiRequest.Builder("demo", "echo")
                .param("message", "হ্যালো NativeRuntimeContracts!")
                .build();

        appendLog("RPC প্রেরণ: path=" + request.getPath() + ", action=" + request.getAction());
        NativeApiResponse response = lib.dispatchApiRequest(request);

        if (response.isSuccess()) {
            appendLog("RPC উত্তর সফল (" + response.getStatusCode() + "): " + response.getData());
        } else {
            appendLog("RPC ত্রুটি: " + (response.getError() != null ? response.getError().getMessage() : "Unknown"));
        }
    }

    private void testAnalyticsAndMetrics() {
        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();

        Map<String, Object> params = new HashMap<>();
        params.put("ui_action", "button_click");
        params.put("screen", "MainActivity");
        AnalyticsEvent event = new AnalyticsEvent("test_event_dispatched", params);
        appendLog("অ্যানালিটিক্স ইভেন্ট: " + event.getName() + " params=" + event.getParameters());

        PerformanceMetric metric = new PerformanceMetric("ui_render_time", 18.4, "ms");
        appendLog("পারফরম্যান্স মেট্রিক: " + metric.getMetricName() + " = " + metric.getValue() + metric.getUnit());

        lib.log(LogLevel.INFO, "MainActivity", "ইভেন্ট ও মেট্রিক ট্র্যাকিং সম্পন্ন");
    }

    private void testDtoTelemetry() {
        DeviceInfoDTO device = new DeviceInfoDTO(
                Build.MANUFACTURER,
                Build.MODEL,
                Build.VERSION.RELEASE,
                Build.VERSION.SDK_INT,
                "DEMO-UUID-" + System.currentTimeMillis() % 10000,
                Runtime.getRuntime().totalMemory()
        );
        appendLog("ডিভাইস DTO: " + device.getManufacturer() + " " + device.getModel() +
                ", Android " + device.getOsVersion() + " (SDK " + device.getSdkInt() + ")");

        BatteryStatusDTO battery = new BatteryStatusDTO(92, false, "BATTERY", 31.5f);
        appendLog("ব্যাটারি DTO: লেভেল=" + battery.getLevel() + "%, চার্জিং=" + battery.isCharging() +
                ", প্লাগ=" + battery.getPluggedType() + ", তাপমাত্রা=" + battery.getTemperature() + "°C");
    }

    private void releaseRuntime() {
        NativeRuntimeContracts.getInstance().release();
        appendLog("রিলিজ সম্পন্ন: সমস্ত রিসোর্স মুক্ত করা হয়েছে এবং স্টেট রিসেট হয়েছে");
        updateStatusDisplay(RuntimeState.UNINITIALIZED);
    }

    private void updateStatusDisplay(@NonNull RuntimeState state) {
        mTvStatus.setText(state.name());
        switch (state) {
            case READY:
                mTvStatus.setTextColor(Color.parseColor("#2E7D32"));
                mTvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
                break;
            case SUSPENDED:
                mTvStatus.setTextColor(Color.parseColor("#E65100"));
                mTvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"));
                break;
            case INITIALIZING:
                mTvStatus.setTextColor(Color.parseColor("#1565C0"));
                mTvStatus.setBackgroundColor(Color.parseColor("#E3F2FD"));
                break;
            default:
                mTvStatus.setTextColor(Color.parseColor("#C62828"));
                mTvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
                break;
        }
    }

    private void appendLog(@NonNull String log) {
        String timestamp = mTimeFormat.format(new Date());
        String current = mTvLogOutput.getText().toString();
        mTvLogOutput.setText(current + "[" + timestamp + "] " + log + "\n");
    }
}

