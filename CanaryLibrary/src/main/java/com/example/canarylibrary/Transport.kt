package com.example.canarylibrary

class Transport(val name: String, typeString: String, val configPath: String)
{
    val serverIP: String = ""
    val port: Int = 0
}

enum class TransportType(val server: String)
{
    replicant("replicant"),
    shadowsocks("shadowsocks")
}

enum class TransportConfig
{
    //
}