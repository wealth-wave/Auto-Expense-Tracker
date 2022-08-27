package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.categories.FetchCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class CategorySuggestionViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<List<String>>(emptyList())
    val categoriesState: StateFlow<List<String>>
        get() = _categoriesState

    suspend fun getCategories(name: String) {
        fetchCategoriesUseCase.fetchCategoriesByNameLike(name).first().also { categories ->
            _categoriesState.value = categories.toMutableList().apply {
                if (contains(name).not()) {
                    add(name)
                }
            }
        }
    }
}
