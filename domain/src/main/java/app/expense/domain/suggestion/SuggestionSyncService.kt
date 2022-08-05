package app.expense.domain.suggestion

import android.Manifest
import androidx.annotation.RequiresPermission
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.domain.suggestion.detector.SuggestionDetector
import app.expense.model.SuggestionDTO
import java.util.concurrent.TimeUnit

class SuggestionSyncService(
    private val suggestionSyncAPI: SuggestionSyncAPI,
    private val smsReadAPI: SMSReadAPI,
    private val suggestionDetector: SuggestionDetector
) {


    @RequiresPermission(Manifest.permission.READ_SMS)
    suspend fun sync() {
        val startTime = System.currentTimeMillis()
        val lastSyncedTime =
            when {
                suggestionSyncAPI.getLastSyncedTime() != null -> suggestionSyncAPI.getLastSyncedTime()
                else -> (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30))
            }
        val suggestions: List<Suggestion> =
            smsReadAPI.getAllSms(lastSyncedTime).mapNotNull { smsMessage ->
                suggestionDetector.detectSuggestions(smsMessage)

            }

        suggestionSyncAPI.storeSuggestions(suggestions.map { suggestion ->
            SuggestionDTO(
                amount = suggestion.amount,
                paidTo = suggestion.paidTo,
                time = suggestion.time,
                referenceMessage = suggestion.referenceMessage,
                referenceMessageSender = suggestion.referenceMessageSender
            )
        })

        suggestionSyncAPI.setLastSyncedTime(startTime)
    }
}