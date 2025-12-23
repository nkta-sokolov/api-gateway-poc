# Simple Multimodule Gradle Project

## Project Overview
The project consists of multiple **independent modules**.  
Each module has its **own Gradle Wrapper**, allowing isolated builds and tests.

## Module-level Commands

### Run Unit Tests
```bash
./gradlew test
```

### Build Executable JAR
Runs **unit** and **integration** tests and build executable JAR:
```bash
./gradlew clean build
```

## Docker

### Build JARs and Docker Images
```bash
./build.sh
```

## Service Management

### Run Services
```bash
./run.sh
```

### Stop Services
```bash
./stop.sh
```

## Notes
- Commands are executed from the module api-gateway-poc unless stated otherwise.
- Ensure Docker is running before executing Docker-related scripts.