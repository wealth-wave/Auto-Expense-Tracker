package app.expense.api

import app.expense.db.daos.PaidToDAO
import app.expense.db.model.PaidToDTO

/**
 * API class to expose PaidTo related logics.
 */
class PaidToAPI(private val paidToDAO: PaidToDAO) {

    /**
     * Store paidTo/Merchant info.
     */
    suspend fun storePaidTo(paidToDTO: PaidToDTO) {
        paidToDAO.insert(paidToDTO)
    }

    /**
     * Get PaidTo from DB.
     */
    fun getPaidTo(name: String) =
        paidToDAO.fetchPaidTo(name)
}
