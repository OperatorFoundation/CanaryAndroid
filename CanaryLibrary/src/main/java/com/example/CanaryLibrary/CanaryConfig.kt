package com.example.CanaryLibrary

import kotlinx.serialization.Serializable

@Serializable
data class CanaryConfig<out T : Any>(val serverIP: String, val serverPort: Int, val transportConfig: T)
{

}
