package com.roni.library.contracts;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * NativeRuntimeContracts — main entry point.
 *
 * <p>Usage:
 * <pre>
 *   NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
 *   lib.init(context, new NativeRuntimeContractsConfig.Builder().build());
 * </pre>
 *
 * @author  infoadmstudio2020-cyber
 * @version 1.0.0
 */
public final class NativeRuntimeContracts {

    private static volatile NativeRuntimeContracts sInstance;
    private NativeRuntimeContractsConfig mConfig;
    private boolean mInitialized;

    /** Private constructor — use {@link #getInstance()}. */
    private NativeRuntimeContracts() {}

    /**
     * Returns the singleton instance of {@link NativeRuntimeContracts}.
     *
     * @return singleton instance
     */
    @NonNull
    public static NativeRuntimeContracts getInstance() {
        if (sInstance == null) {
            synchronized (NativeRuntimeContracts.class) {
                if (sInstance == null) {
                    sInstance = new NativeRuntimeContracts();
                }
            }
        }
        return sInstance;
    }

    /**
     * Initialize the library.
     *
     * @param context application context
     * @param config  library configuration
     */
    public void init(@NonNull Context context, @NonNull NativeRuntimeContractsConfig config) {
        if (mInitialized) {
            NativeRuntimeContractsLogger.w("NativeRuntimeContracts", "Already initialized");
            return;
        }
        mConfig      = config;
        mInitialized = true;
        NativeRuntimeContractsLogger.i("NativeRuntimeContracts", "Initialized — version 1.0.0");
    }

    /**
     * Check if the library has been initialized.
     *
     * @return true if initialized
     */
    public boolean isInitialized() { return mInitialized; }

    /**
     * Return the current configuration.
     *
     * @return config, or null if not initialized
     */
    public NativeRuntimeContractsConfig getConfig() { return mConfig; }

    /** Release all resources held by this instance. */
    public void release() {
        mInitialized = false;
        mConfig      = null;
        sInstance    = null;
    }
}
