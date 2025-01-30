package com.tonholo.photos

actual fun getPlatform(): Platform = object : Platform {
    override val name: String = "JVM Hot Reload"
}