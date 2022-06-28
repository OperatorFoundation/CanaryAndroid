package com.example.canarylibrary

var saveDirectoryPath = ""

var testingTransports = arrayOf<Transport>()
val possibleTransportNames = arrayOf("shadowsocks", "replicant")

val httpRequestString = "GET / HTTP/1.0\r\nConnection: close\r\n\r\n"
val canaryString = "Yeah!\n"

// Web Tests

val facebook = WebTest("https://www.facebook.com/", "facebook", 443)
val cnn = WebTest("https://www.cnn.com/", "cnn", 443)
val wikipedia = WebTest("https://www.wikipedia.org/", "wikipedia", 443)
val ymedio = WebTest("https://www.14ymedio.com", "14ymedio", 443)
val cnet = WebTest("https://www.cubanet.org", "cnet", 443)
val diario = WebTest("https://diariodecuba.com", "diario", 443)
val allWebTests = arrayOf(facebook, cnn, wikipedia, ymedio, cnet, diario)
val stateDirectoryPath = "TransportState"
val resultsFileName = "CanaryResults.csv"

