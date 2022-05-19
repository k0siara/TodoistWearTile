package com.patrykkosieradzki.todoist.wear.tile.auth

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationCodeGenerator @Inject constructor() {
    fun createVerificationCode() = UUID.randomUUID().toString()
}