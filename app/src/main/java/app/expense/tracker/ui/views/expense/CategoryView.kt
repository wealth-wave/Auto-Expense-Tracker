package app.expense.tracker.ui.views.expense

import AutoCompleteTextField
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
import app.expense.presentation.viewModels.CategoriesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryView(
    category: MutableState<String>,
    viewModel: CategoriesViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val suggestions = viewModel.categoriesState.collectAsState()

    AutoCompleteTextField(
        label = "Category",
        value = category.value,
        suggestions = suggestions.value,
        onCategoryEntered = { value ->
            category.value = value
            coroutineScope.launch {
                viewModel.getCategories(value)
            }
        },
        onCategorySelect = { value ->
            category.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}