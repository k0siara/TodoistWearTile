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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.ensureActive

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
    private var currentTextFromSpeechDeferred: CompletableDeferred<String?>? = null

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

    @OptIn(InternalCoroutinesApi::class)
    override suspend fun getTextFromSpeechAsync(): Deferred<String?> {
        return CompletableDeferred<String?>().also { deferred ->
            val currentDeferred = currentTextFromSpeechDeferred
            if (currentDeferred != null && currentDeferred.isActive) {
                deferred.completeExceptionally(IllegalStateException("Already requested"))
                return@also
            }
            currentTextFromSpeechDeferred = deferred
            currentTextFromSpeechDeferred?.invokeOnCompletion(onCancelling = true) {
                currentTextFromSpeechDeferred = null
            }

            try {
                val activity = activityRef?.get() ?: throw WearException.MissingActivityException()
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                }
                activity.startActivityForResult(intent, GET_TEXT_FROM_SPEECH_REQUEST_CODE)
                deferred.ensureActive()
            } catch (e: Exception) {
                deferred.ensureActive()
                deferred.completeExceptionally(e)
                currentTextFromSpeechDeferred = null
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
            currentTextFromSpeechDeferred?.cancel()
            currentTextFromSpeechDeferred = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == GET_TEXT_FROM_SPEECH_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            .let { results -> results?.get(0) }
                        currentTextFromSpeechDeferred?.complete(text)
                    } catch (e: Exception) {
                        currentTextFromSpeechDeferred?.complete(null)
                    }
                }
                else -> currentTextFromSpeechDeferred?.complete(null)
            }
            currentTextFromSpeechDeferred = null
            return true
        }

        return false
    }

    companion object {
        private const val GET_TEXT_FROM_SPEECH_REQUEST_CODE = 21
    }
}
