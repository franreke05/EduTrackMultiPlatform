package com.franciscor.edutrackmultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform