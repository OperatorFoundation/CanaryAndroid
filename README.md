# CanaryAndroid
 
A Kotlin library designed to power Android apps. Based on the [original project](https://github.com/OperatorFoundation/CanaryLibrary.git) written in Swift.

Canary is a tool for testing transport connections and gathering the packets for analysis. 

Canary will run a series of transport tests based on the configs that you provide. It is possible to test each transport on a different transport server based on what host information is provided in the transport config files. 

Currently only [Shadow](https://github.com/OperatorFoundation/ShapeshifterAndroidKotlin.git) tests are supported. Replicant support is underway, and will be capable of mimicking other transports when it is complete.
