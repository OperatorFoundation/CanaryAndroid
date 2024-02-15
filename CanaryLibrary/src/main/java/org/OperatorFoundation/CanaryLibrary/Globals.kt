package org.OperatorFoundation.CanaryLibrary

var testingTransports = arrayOf<Transport>()

val possibleTransportTypes = arrayOf(TransportType.Shadow)
const val httpRequestString = "GET / HTTP/1.0\r\nConnection: close\r\n\r\n"
const val canaryString = "Yeah!"
const val resultsFileName = "CanaryResults"
const val resultsFileExtension = ".csv"

