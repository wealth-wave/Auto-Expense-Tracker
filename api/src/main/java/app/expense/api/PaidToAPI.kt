package app.expense.api

import app.expense.db.PaidToDAO
import app.expense.model.PaidToDTO

class PaidToAPI(private val paidToDAO: PaidToDAO) {

    suspend fun storePaidTo(paidToDTO: PaidToDTO) {
        paidToDAO.insert(paidToDTO)
    }

    fun getPaidTo(name: String) =
        paidToDAO.fetchPaidTo(name)
}