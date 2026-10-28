package com.roni.library.contracts.interfaces;

import androidx.annotation.NonNull;

/**
 * Listener interface for NativeRuntimeContracts events.
 *
 * @author infoadmstudio2020-cyber
 */
public interface INativeRuntimeContractsListener {

    /**
     * Called when the library is successfully initialized.
     */
    void onInitialized();

    /**
     * Called when an error occurs.
     *
     * @param errorCode numeric error code
     * @param message   human-readable message
     */
    void onError(int errorCode, @NonNull String message);
}
