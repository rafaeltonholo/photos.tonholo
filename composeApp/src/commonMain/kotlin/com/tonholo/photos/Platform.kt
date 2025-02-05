package com.tonholo.photos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
