package com.roni.library.contracts.common;

/**
 * Standard runtime states for subsystems in the NativeRuntime ecosystem.
 *
 * State flow:
 * UNINITIALIZED -> INITIALIZING -> READY -> SUSPENDED -> TERMINATED
 *
 * @author infoadmstudio2020-cyber
 */
public enum RuntimeState {

    /** Initial state before bootstrapping begins. */
    UNINITIALIZED,

    /** Active bootstrapping and dependency resolution in progress. */
    INITIALIZING,

    /** Fully booted, active and handling requests. */
    READY,

    /** Temporarily paused or backgrounded. */
    SUSPENDED,

    /** Completely stopped, cleaned up and disposed. */
    TERMINATED;

    /**
     * Check if the current state is final (cannot transition further).
     *
     * @return true if TERMINATED
     */
    public boolean isTerminal() {
        return this == TERMINATED;
    }

    /**
     * Check if the runtime is ready to accept commands.
     *
     * @return true if READY
     */
    public boolean isOperational() {
        return this == READY;
    }
}
