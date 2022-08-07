package app.expense.tracker.ui.views.expense

import AutoCompleteTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.CategoriesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryView(
    categories: MutableState<List<String>>,
    viewModel: CategoriesViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val suggestions = viewModel.categoriesState.collectAsState()
    val category = rememberSaveable() { mutableStateOf("") }

    Column {
        AutoCompleteTextField(
            value = category.value,
            label = "Category",
            suggestions = suggestions.value,
            onCategoryEntered = { value ->
                category.value = value
                coroutineScope.launch {
                    viewModel.getCategories(value)
                }
            },
            onCategorySelect = { value ->
                categories.value = categories.value.toMutableList().apply { add(value) }
                category.value = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        if (categories.value.isNotEmpty()) {
            LazyRow(modifier = Modifier.padding(16.dp)) {
                items(categories.value) { item ->
                    InputChip(
                        selected = true,
                        label = { Text(text = item) },
                        onClick = {
                            categories.value =
                                categories.value.toMutableList().apply { remove(item) }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remove category"
                            )
                        })
                }
            }
        }
    }

}