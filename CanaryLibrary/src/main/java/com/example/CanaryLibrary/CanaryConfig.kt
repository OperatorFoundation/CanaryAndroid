package org.OperatorFoundation.CanaryLibrary

import kotlinx.serialization.Serializable

@Serializable
data class CanaryConfig<out T : Any>(val serverIP: String, val port: Int, val transportConfig: T)
{

}
