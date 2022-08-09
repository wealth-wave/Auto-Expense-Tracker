package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.PaidToViewModel
import app.expense.tracker.ui.utils.AutoCompleteTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaidToView(
    paidTo: MutableState<String>,
    viewModel: PaidToViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val suggestions = viewModel.paidToState.collectAsState()

    AutoCompleteTextField(
        label = "Paid To",
        value = paidTo.value,
        suggestions = suggestions.value,
        onCategoryEntered = { value ->
            paidTo.value = value
            coroutineScope.launch {
                viewModel.getPaidTos(value)
            }
        },
        onCategorySelect = { value ->
            paidTo.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
