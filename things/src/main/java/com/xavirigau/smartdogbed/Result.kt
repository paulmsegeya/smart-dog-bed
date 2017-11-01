package com.xavirigau.smartdogbed

sealed class Result {
    abstract fun name(): String
}

object Busy : Result() {
    override fun name() = "busy"
}

object Idle : Result() {
    override fun name() = "idle"
}
