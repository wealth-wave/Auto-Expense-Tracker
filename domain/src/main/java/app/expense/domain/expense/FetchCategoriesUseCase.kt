package app.expense.domain.expense

import app.expense.api.CategoryAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchCategoriesUseCase(
    private val categoryAPI: CategoryAPI
) {

    fun fetchCategories(name: String): Flow<List<String>> {
        return categoryAPI.getCategories(name).map { categories ->
            categories.map { category ->
                category.name
            }
        }
    }
}