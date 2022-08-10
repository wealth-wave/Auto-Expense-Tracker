package app.expense.api

import app.expense.db.daos.CategoryDAO
import app.expense.model.CategoryDTO

class CategoryAPI(private val categoryDAO: CategoryDAO) {

    suspend fun storeCategories(categories: List<CategoryDTO>) {
        categoryDAO.insertAll(categories)
    }

    fun getCategories(name: String) =
        categoryDAO.fetchCategories(name)
}
