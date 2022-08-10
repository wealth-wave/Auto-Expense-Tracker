package app.expense.tracker.ui.views.addExpense

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.AddExpenseViewModel
import app.expense.tracker.R
import app.expense.tracker.ui.utils.AmountInputDialog
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale.getDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onGoBack: () -> Unit,
    suggestionId: Long? = null,
    expenseId: Long? = null,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val addExpenseViewState = viewModel.addExpenseViewState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val amount =
        rememberSaveable(addExpenseViewState.amount) { mutableStateOf(addExpenseViewState.amount) }
    val amountDialogOpen = remember { mutableStateOf(false) }
    val paidTo =
        rememberSaveable(addExpenseViewState.paidTo) { mutableStateOf(addExpenseViewState.paidTo) }
    val categories =
        rememberSaveable(addExpenseViewState.categories) { mutableStateOf(addExpenseViewState.categories) }
    val time =
        rememberSaveable(addExpenseViewState.time) { mutableStateOf(addExpenseViewState.time) }
    val isFormValid = remember(amount.value) {
        derivedStateOf {
            amount.value > 0.0 && paidTo.value.isNotBlank()
        }
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = "${expenseId ?: ""} ${suggestionId ?: ""}") {
        viewModel.getAddExpenseViewState(expenseId, suggestionId)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = if (expenseId == null) {
                            stringResource(R.string.add_expense)
                        } else {
                            stringResource(R.string.edit_expense)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onGoBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                actions = {
                    if (suggestionId != null || expenseId != null) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (suggestionId != null) {
                                        viewModel.deleteSuggestion(suggestionId)
                                    } else if (expenseId != null) {
                                        viewModel.deleteExpense(expenseId)
                                    }
                                    onGoBack()
                                }
                            },
                            enabled = isFormValid.value
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_expense)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.addExpense(
                                    expenseId = expenseId,
                                    suggestionId = suggestionId,
                                    amount = amount.value,
                                    paidTo = paidTo.value,
                                    categories = categories.value,
                                    time = time.value
                                )
                                onGoBack()
                            }
                        },
                        enabled = isFormValid.value
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.add_expense)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (amountDialogOpen.value) {
            AmountInputDialog(
                amount = amount.value,
                onAmountEntered = { value ->
                    amount.value = value
                    amountDialogOpen.value = false
                },
                onDismiss = {
                    amountDialogOpen.value = false
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            if (addExpenseViewState.suggestionMessage != null) {
                Card(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                    Text(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding)),
                        text = addExpenseViewState.suggestionMessage ?: "",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {
                            amountDialogOpen.value = true
                        })
                ) {
                    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                        Text(
                            text = stringResource(R.string.total_amount),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                        Text(
                            text = NumberFormat.getCurrencyInstance().format(amount.value),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                Card(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.default_padding))
                            .clickable(onClick = {
                                val calendar = Calendar.getInstance()
                                calendar.timeInMillis = time.value

                                DatePickerDialog(
                                    context,
                                    { datePicker, _, _, _ ->
                                        calendar.set(Calendar.YEAR, datePicker.year)
                                        calendar.set(Calendar.MONTH, datePicker.month)
                                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                                        time.value = calendar.timeInMillis
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            })
                    ) {
                        Text(
                            text = stringResource(R.string.pick_date),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                        Text(
                            text = SimpleDateFormat("dd-MMM-yyyy", getDefault()).format(time.value),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            PaidToView(paidTo = paidTo)
            CategoryView(categories = categories)
        }
    }
}
