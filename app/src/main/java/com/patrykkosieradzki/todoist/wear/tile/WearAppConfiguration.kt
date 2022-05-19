package com.patrykkosieradzki.todoist.wear.tile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WearAppConfiguration @Inject constructor() {
    val todoistTokenApiUrl = "https://todoist.com/"
    val todoistApiUrl = "https://api.todoist.com/rest/v1/"

    val todoistClientId = "2bca0375698b4ef393d19a88a024e66b"
    val todoistClientSecret = ""
}