package app.expense.domain.categories

import app.expense.api.CategoryAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchCategoriesUseCase(
    private val categoryAPI: CategoryAPI
) {

    fun fetchCategoriesByNameLike(name: String): Flow<List<String>> {
        return categoryAPI.getCategoriesLike(name).map { categories ->
            categories.map { category ->
                category.name
            }
        }
    }
}
