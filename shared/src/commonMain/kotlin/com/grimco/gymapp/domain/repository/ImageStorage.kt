package com.grimco.gymapp.domain.repository

interface ImageStorage {
    fun saveImage(name: String, bytes: ByteArray): String?
}