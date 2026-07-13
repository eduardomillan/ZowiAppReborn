# ZOWI ROBOTICS PACKAGE

## Overview

The `com.bq.robotic` package contains the complete Android application code for interacting with BQ Zowi robots, designed to manage and program these educational robotic kits.

## Subpackages

### droid2ino

The `droid2ino` subpackage provides the Android user interface and Bluetooth communication layer for connecting to Zowi robots.

#### Key Components:

- **BluetoothConnection** (`BluetoothConnection.java`): Core Bluetooth communication handler that manages connection state, accepts incoming connections, and maintains connected threads. Supports both duplex (bidirectional) and simplex (read-only) communication modes.

- **DeviceListDialog** (`DeviceListDialog.java`): Custom dialog UI component that allows users to browse and select paired or discovered Zowi devices. Includes scanning functionality for finding new devices.

- **BaseBluetoothConnectionActivity** (`BaseBluetoothConnectionActivity.java`): Abstract base activity for applications that need Bluetooth connectivity. Handles connection lifecycle events, Bluetooth adapter management, and provides message handling for connection status updates.

- **BaseBluetoothSendOnlyActivity** (`BaseBluetoothSendOnlyActivity.java`): Specialized base activity for applications that only need to read data from Zowi robots (e.g., monitoring mode).

- **DialogListener** (`DialogListener.java`): Callback interface for handling user selections from the device list dialog.

- **DeviceListDialogStyle** (`DeviceListDialogStyle.java`): Manages UI styling for the device selection dialog, allowing customization of title text views.

- **Droid2InoConstants** (`Droid2InoConstants.java`): Collection of Android application-specific constants including Bluetooth UUID (00001101-0000-1000-8000-00805F9B34FB), message types, connection states, and UI string identifiers.

**Purpose**: This subpackage provides the necessary Android framework integration, user interface components, and Bluetooth connection infrastructure to enable device discovery and communication from the Android application.

### protocolSTK500v1

The `protocolSTK500v1` subpackage implements the STK500v1 programmer protocol used to upload firmware to Zowi robots. It handles the low-level communication protocol required for programming the robot's microcontroller.

#### Key Components:

- **STK500v1** (`STK500v1.java`): Main protocol handler class that manages the complete programming workflow including synchronization, firmware upload, error recovery, and progress tracking. Supports multiple protocol states and timeout recovery mechanisms.

- **Reader** (`Reader.java`): Multi-threaded class that reads incoming data from Zowi robots using a state machine pattern. Implements the STK500v1 protocol reading logic with timeout handling and error recovery.

- **Logger** (`Logger.java`): Interface for logging protocol activities with different severity levels (verbose, info, warning, error). Provides standardized logging across the protocol stack.

- **Hex** (`Hex.java`): Utility class for parsing HEX formatted firmware files. Validates checksums and extracts binary data for programming operations.

- **IReader** (`IReader.java`): Interface defining the reader contract with methods for reading data, managing state, and controlling the reading thread.

- **IReaderState** (`IReaderState.java`): Interface for reader state objects that participate in a state machine architecture.

- **EReaderState** (`EReaderState.java`): Enumeration defining all possible states in the reader state machine (STOPPED, STARTING, WAITING, READING, etc.).

- **ConstantsStk500v1** (`ConstantsStk500v1.java`): Collection of protocol command constants used during communication with the STK500v1 programmer (e.g., STK_GET_SYNC, STK_PROG_FLASH, STK_OK, etc.).

- **TimeoutValues** (`TimeoutValues.java`): Configuration class defining timeout values for various communication operations.

**Purpose**: This subpackage provides the core firmware programming and communication protocol implementation that enables the Android application to upload firmware to Zowi robots using the STK500v1 programmer interface.

## Integration

These two subpackages work together to provide a complete solution for managing Zowi robots:

1. The **droid2ino** subpackage handles the Android application framework and user interaction
2. The **protocolSTK500v1** subpackage handles the low-level communication protocol for firmware programming
3. Together they enable complete functionality: device discovery, Bluetooth connection, and firmware upload to Zowi robots

**Usage Context**: This package is part of the "ZowiAppReborn" project, which revives and enhances the original 2016 BQ Android app for managing Zowi educational robots.