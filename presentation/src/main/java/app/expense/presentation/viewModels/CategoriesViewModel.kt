package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.FetchCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<List<String>>(emptyList())
    val categoriesState: StateFlow<List<String>>
        get() = _categoriesState

    suspend fun getCategories(name: String) {
        fetchCategoriesUseCase.fetchCategories(name).collect { categories ->
            _categoriesState.value = categories.toMutableList().apply {
                add(name)
            }
        }
    }
}
