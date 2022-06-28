package com.example.canarylibrary

class Transport(val name: String, typeString: String, val configPath: String)
{

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