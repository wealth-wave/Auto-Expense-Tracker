package app.expense.domain.paidTo

import app.expense.api.PaidToAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchPaidToUseCase(
    private val paidToAPI: PaidToAPI
) {

    fun fetchPaidTo(name: String): Flow<List<String>> {
        return paidToAPI.getPaidTo(name).map { paidTos ->
            paidTos.map { paidTo ->
                paidTo.name
            }
        }
    }
}
