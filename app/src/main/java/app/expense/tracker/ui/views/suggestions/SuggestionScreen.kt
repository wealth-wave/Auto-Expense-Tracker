package app.expense.tracker.ui.views.suggestions

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.SuggestionListViewModel
import app.expense.presentation.viewStates.SuggestionListState
import app.expense.tracker.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SuggestionsScreen(
    onAddSuggestion: (suggestionId: Long) -> Unit,
    viewModel: SuggestionListViewModel = hiltViewModel()
) {
    val suggestionListState =
        viewModel.getSuggestionListState().collectAsState(initial = SuggestionListState()).value
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
        Text(
            text = stringResource(R.string.suggestions),
            style = MaterialTheme.typography.titleMedium
        )

        val smsPermissionState = rememberPermissionState(
            Manifest.permission.READ_SMS
        )

        when (smsPermissionState.status) {
            PermissionStatus.Granted -> {
                if (suggestionListState.dateSuggestionsMap.isEmpty()) {
                    ShowEmptySuggestion()
                } else {
                    ShowSuggestions(
                        suggestionListState = suggestionListState,
                        onAddSuggestion = onAddSuggestion,
                        onDeleteSuggestion = { suggestionId ->
                            coroutineScope.launch {
                                viewModel.deleteSuggestion(suggestionId)
                            }
                        }
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.default_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.sms_permission_needed),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                    Button(onClick = { smsPermissionState.launchPermissionRequest() }) {
                        Text(stringResource(R.string.request_permission))
                    }
                }
            }
        }
    }
}

@Composable
fun ShowEmptySuggestion() {
    Text(
        text = stringResource(R.string.empty_suggestions_message),
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun ShowSuggestions(
    suggestionListState: SuggestionListState,
    onAddSuggestion: (suggestionId: Long) -> Unit,
    onDeleteSuggestion: (suggestionId: Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = dimensionResource(id = R.dimen.default_padding),
            bottom = dimensionResource(id = R.dimen.default_padding)
        )
    ) {
        items(suggestionListState.dateSuggestionsMap.size) { pos ->
            val dateString = suggestionListState.dateSuggestionsMap.keys.toList()[pos]
            val suggestionItems = suggestionListState.dateSuggestionsMap[dateString]

            Text(
                text = dateString,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_gap)),
                style = MaterialTheme.typography.labelLarge
            )
            suggestionItems?.forEach { suggestionItem ->
                Card(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.large_gap))) {
                    Column(
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.default_padding),
                            end = dimensionResource(id = R.dimen.default_padding),
                            top = dimensionResource(id = R.dimen.default_padding)
                        )
                    ) {
                        Text(
                            text = suggestionItem.message,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = suggestionItem.amount,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = {
                                onAddSuggestion(suggestionItem.id)
                            }) {
                                Text(text = stringResource(R.string.add_to_expense))
                            }
                            TextButton(onClick = {
                                onDeleteSuggestion(suggestionItem.id)
                            }) {
                                Text(text = stringResource(R.string.ignore))
                            }
                        }
                    }
                }
            }
        }
    }
}
