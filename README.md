# CanaryAndroid

An example Kotlin application to demonstrate one way to use the [Canary Library for Android](https://github.com/OperatorFoundation/CanaryAndroid.git).

Canary is a tool for testing transport connections and recording the results in an easy to share csv format.

Canary will run a series of transport tests based on the configs that you provide. It is possible to test each transport on a different transport server based on what host information is provided in the transport config files.

Currently [Shadow](https://github.com/OperatorFoundation/ShadowSwift.git) and [Starbridge](https://github.com/OperatorFoundation/Starbridge.git) tests are supported. Replicant support is underway, and will be capable of mimicking other transports when it is complete.

## Running Canary

*This example app requires Android 4.2 (minSdk of 17) or higher.*

### Add the library as a dependency. 
- The following link provides instructions on adding this dependency:
- (https://jitpack.io/#OperatorFoundation/CanaryAndroid)

### To run Canary transport connection tests:
- In the app select the directory that has the config files for the transports that you want to use for connection tests (transport client config files must be saved in the file management system of the device).
- Select the number of times you would like the tests to be repeated.
- Tap the "Run Tests" button to start the connection tests.

### To view transport connection test results:
- Tap the "show results" button
- Tap the result file of your choice (A list of file names will be shown, each file is named with the day the tests were run)
- The following screen will show the results of each test in the file in text format. 
- Tap the share button in the top right-hand corner to share the results as a csv file.

## Transport config files

See the documentation for a specific transport if you need instructions on how to generate config files for Canary supported transports:
- [Starbridge](https://github.com/OperatorFoundation/Starbridge.git)
- [ShadowSwift](https://github.com/OperatorFoundation/ShadowSwift.git)

Config files must conform to the following convention:
- They must be valid JSON files that conform to the specific transport requirements (e.g. Shadow, Starbridge).
- File names must include the name of the specific transport (e.g. "ShadowClientConfig.json", "StarbridgeClientConfig.json").

