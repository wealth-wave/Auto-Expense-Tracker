package app.expense.api

import app.expense.db.daos.CategoryDAO
import app.expense.db.model.CategoryDTO

/**
 * Category API to expose category related operations.
 */
class CategoryAPI(private val categoryDAO: CategoryDAO) {

    /**
     * Store List of Categories to DB.
     */
    suspend fun storeCategories(categories: List<CategoryDTO>) {
        categoryDAO.insertAll(categories)
    }

    /**
     * Fetch List of Categories from DB where it matches the name like.
     */
    fun getCategoriesLike(name: String) =
        categoryDAO.fetchCategoriesLike(name)
}
