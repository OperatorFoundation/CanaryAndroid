package com.example.CanaryLibrary

var testingTransports = arrayOf<Transport>()

val possibleTransportTypes = arrayOf(TransportType.shadow)
val httpRequestString = "GET / HTTP/1.0\r\nConnection: close\r\n\r\n"
val canaryString = "Yeah!\n"
val resultsFileName = "CanaryResults.csv"

