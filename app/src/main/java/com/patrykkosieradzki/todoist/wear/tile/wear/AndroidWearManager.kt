package com.patrykkosieradzki.todoist.wear.tile.wear

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.wear.widget.ConfirmationOverlay
import com.patrykkosieradzki.todoist.wear.tile.WearException
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

interface WearManagerHost {
    fun attach(activity: Activity)
    fun detach(activity: Activity, willReattach: Boolean)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
}

interface WearManager {
    fun showConfirmationOverlay(
        type: Int = ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION,
        message: String,
        duration: Int = 1500,
        onAnimationFinished: () -> Unit = {}
    )

    suspend fun getTextFromSpeechAsync(): Deferred<String?>
}

@Singleton
class AndroidWearManager @Inject constructor() : WearManager, WearManagerHost {

    private var activityRef: WeakReference<Activity>? = null
    private var currentTextFromSpeechDeffered: CompletableDeferred<String?>? = null

    override fun showConfirmationOverlay(
        type: Int,
        message: String,
        duration: Int,
        onAnimationFinished: () -> Unit
    ) {
        val activity = activityRef?.get() ?: return

        ConfirmationOverlay()
            .setType(type)
            .setDuration(duration)
            .setMessage(message as CharSequence)
            .setOnAnimationFinishedListener {
                onAnimationFinished.invoke()
            }.showOn(activity)
    }

    override suspend fun getTextFromSpeechAsync(): Deferred<String?> {
        return CompletableDeferred<String?>().also { deferred ->
            if (currentTextFromSpeechDeffered != null) {
                deferred.completeExceptionally(IllegalStateException("Already requested"))
                return@also
            }
            currentTextFromSpeechDeffered = deferred
            try {
                val activity = activityRef?.get() ?: throw WearException.MissingActivityException()
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                }
                activity.startActivityForResult(intent, GET_TEXT_FROM_SPEECH_REQUEST_CODE)
            } catch (e: Exception) {
                deferred.completeExceptionally(e)
                currentTextFromSpeechDeffered = null
            }
        }
    }

    override fun attach(activity: Activity) {
        activityRef = WeakReference(activity)
    }

    override fun detach(activity: Activity, willReattach: Boolean) {
        if (activity != activityRef?.get()) return

        activityRef = null
        if (!willReattach) {
            // cancel requests
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == 21) {
            // Handle result and errors
            currentTextFromSpeechDeffered?.complete("text")
            return true
        }

        return false
    }

    companion object {
        private const val GET_TEXT_FROM_SPEECH_REQUEST_CODE = 21
    }
}