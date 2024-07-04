package app.expense.tracker.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.CategorySuggestionViewModel
import app.expense.tracker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryInputDialog(
    onCategoryEntered: (String) -> Unit,
    onDismiss: () -> Unit,
    viewModel: CategorySuggestionViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val suggestions = viewModel.categoriesState.collectAsState().value
    val category = rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
            Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                Column {
                    AutoCompleteTextField(
                        value = category.value,
                        label = stringResource(id = R.string.category),
                        suggestions = suggestions,
                        onCategoryEntered = { value ->
                            category.value = value
                            coroutineScope.launch {
                                viewModel.getCategories(value)
                            }
                        },
                        onCategorySelect = { value ->
                            if(value.isNotEmpty()) {
                                onCategoryEntered(value)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                    Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                        TextButton(
                            enabled = category.value.isNotEmpty(),
                            onClick = {
                                onCategoryEntered(category.value)
                            }
                        ) {
                            Text(text = stringResource(R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}
