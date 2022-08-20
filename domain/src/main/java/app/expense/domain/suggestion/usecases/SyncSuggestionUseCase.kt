package app.expense.domain.suggestion.usecases

import android.Manifest
import androidx.annotation.RequiresPermission
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.db.model.SuggestionDTO
import app.expense.domain.suggestion.detector.SuggestionDetector
import app.expense.domain.suggestion.mappers.SMSMessageDataMapper
import app.expense.domain.suggestion.models.Suggestion
import java.util.concurrent.TimeUnit

/**
 * Sync the New SMS and deducts suggestions.
 */
class SyncSuggestionUseCase(
    private val suggestionSyncAPI: SuggestionSyncAPI,
    private val suggestionsAPI: SuggestionsAPI,
    private val smsReadAPI: SMSReadAPI,
    private val suggestionDetector: SuggestionDetector,
    private val dataMapper: SMSMessageDataMapper
) {

    /**
     * Sync the New SMS and deducts suggestions.
     * Needs SMS Permission.
     */
    @RequiresPermission(Manifest.permission.READ_SMS)
    suspend fun sync(): List<Suggestion> {
        val startTime = System.currentTimeMillis()
        val lastSyncedTime =
            when {
                suggestionSyncAPI.getLastSyncedTime() != null -> suggestionSyncAPI.getLastSyncedTime()
                else -> (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30))
            }
        val suggestions: List<Suggestion> =
            smsReadAPI.getAllSms(lastSyncedTime).mapNotNull { smsMessageDTO ->
                suggestionDetector.detectSuggestions(
                    dataMapper.mapToSMSMessage(
                        smsMessageDTO
                    )
                )
            }

        suggestionsAPI.storeSuggestions(
            suggestions.map { suggestion ->
                SuggestionDTO(
                    amount = suggestion.amount,
                    paidTo = suggestion.paidTo,
                    time = suggestion.time,
                    referenceMessage = suggestion.referenceMessage,
                    referenceMessageSender = suggestion.referenceMessageSender
                )
            }
        )

        suggestionSyncAPI.setLastSyncedTime(startTime)

        return suggestions
    }
}
