package com.roni.library.contracts.app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.roni.library.contracts.NativeRuntimeContracts;
import com.roni.library.contracts.NativeRuntimeContractsConfig;

/**
 * Main Activity demonstrating NativeRuntimeContracts usage.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the library
        NativeRuntimeContractsConfig config = new NativeRuntimeContractsConfig.Builder()
            .debugEnabled(true)
            .tag("App")
            .build();

        NativeRuntimeContracts.getInstance().init(getApplicationContext(), config);

        TextView statusView = findViewById(R.id.tv_status);
        boolean initialized = NativeRuntimeContracts.getInstance().isInitialized();
        statusView.setText("NativeRuntimeContracts initialized: " + initialized);
    }
}
