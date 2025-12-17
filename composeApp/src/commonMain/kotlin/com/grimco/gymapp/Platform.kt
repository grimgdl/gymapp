package com.grimco.gymapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform