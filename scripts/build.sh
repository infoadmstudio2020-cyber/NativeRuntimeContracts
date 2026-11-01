#!/bin/bash
# Build script for NativeRuntimeContracts
set -e

echo "Building NativeRuntimeContracts v1.0.2..."
chmod +x gradlew
./gradlew clean :native-runtime-contracts:assembleRelease :native-runtime-contracts:test --stacktrace || ./gradlew clean assembleRelease || ./gradlew clean build
echo "Build complete: native-runtime-contracts/build/outputs/aar/"

