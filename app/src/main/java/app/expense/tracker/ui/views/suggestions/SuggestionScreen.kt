package app.expense.tracker.ui.views.suggestions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.coroutines.launch

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
                    Card(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_gap))) {
                        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
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
                                    coroutineScope.launch {
                                        viewModel.deleteSuggestion(suggestionItem.id)
                                    }
                                }) {
                                    Text(text = stringResource(R.string.ignore))
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_gap)))
            }
        }
    }
}