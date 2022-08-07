package app.expense.api

import app.expense.db.CategoryDAO
import app.expense.model.CategoryDTO

class CategoryAPI(private val categoryDAO: CategoryDAO) {

    suspend fun storeCategory(categoryDTO: CategoryDTO) {
        categoryDAO.insert(categoryDTO)
    }

    fun getCategories(name: String) =
        categoryDAO.fetchCategories(name)
}