package org.OperatorFoundation.CanaryLibrary

import kotlinx.serialization.Serializable

@Serializable
data class CanaryConfig<out T : Any>(val transportConfig: T)

