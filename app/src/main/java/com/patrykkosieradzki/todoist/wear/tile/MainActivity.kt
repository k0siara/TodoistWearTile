package com.patrykkosieradzki.todoist.wear.tile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.patrykkosieradzki.todoist.wear.tile.theme.WearAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearAppTheme {
                AppContent()
            }
        }
    }
}
