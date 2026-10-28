package com.roni.library.contracts;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link NativeRuntimeContracts}.
 *
 * @author infoadmstudio2020-cyber
 */
public class NativeRuntimeContractsTest {

    @Test
    public void getInstance_returnsSameInstance() {
        NativeRuntimeContracts a = NativeRuntimeContracts.getInstance();
        NativeRuntimeContracts b = NativeRuntimeContracts.getInstance();
        assertSame(a, b);
    }

    @Test
    public void isInitialized_falseBeforeInit() {
        // Reset singleton for test isolation
        NativeRuntimeContracts lib = NativeRuntimeContracts.getInstance();
        assertFalse("Should not be initialized before init()", lib.isInitialized());
    }

    @Test
    public void config_builder_defaults() {
        NativeRuntimeContractsConfig config = new NativeRuntimeContractsConfig.Builder().build();
        assertFalse(config.isDebugEnabled());
        assertEquals("NativeRuntimeContracts", config.getTag());
        assertEquals(30_000, config.getTimeout());
    }
}
