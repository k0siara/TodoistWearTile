package com.patrykkosieradzki.todoist.wear.tile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.patrykkosieradzki.todoist.wear.tile.theme.WearAppTheme
import com.patrykkosieradzki.todoist.wear.tile.wear.WearManagerHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var wearManagerHost: WearManagerHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wearManagerHost.attach(this)

        setContent {
            WearAppTheme {
                AppNavGraph()
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (wearManagerHost.onActivityResult(requestCode, resultCode, data)) {
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wearManagerHost.detach(
            activity = this,
            willReattach = isChangingConfigurations
        )
    }
}
