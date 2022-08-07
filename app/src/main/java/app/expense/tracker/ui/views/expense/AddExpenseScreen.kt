package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.expense.presentation.viewModels.AddExpenseViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    suggestionId: Long? = null,
    expenseId: Long? = null,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val addExpenseViewState = viewModel.addExpenseViewState.collectAsState()
    val amount =
        rememberSaveable(addExpenseViewState.value.amount) { mutableStateOf(addExpenseViewState.value.amount) }
    val paidTo =
        rememberSaveable(addExpenseViewState.value.paidTo) { mutableStateOf(addExpenseViewState.value.paidTo) }
    val categories =
        rememberSaveable(addExpenseViewState.value.categories) { mutableStateOf(addExpenseViewState.value.categories) }
    val time =
        rememberSaveable(addExpenseViewState.value.time) { mutableStateOf(addExpenseViewState.value.time) }
    val isFormValid = derivedStateOf {
        amount.value.toDoubleOrNull() != null
    }

    LaunchedEffect(key1 = "${expenseId ?: ""} ${suggestionId ?: ""}") {
        viewModel.getAddExpenseViewState(expenseId, suggestionId)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = "Add Expense") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                                    navController.popBackStack()
                                }
                            },
                            enabled = isFormValid.value
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete Expense"
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
                                navController.popBackStack()
                            }
                        },
                        enabled = isFormValid.value
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Add Expense")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                label = { Text(text = "Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = amount.value,
                onValueChange = { value ->
                    amount.value = value
                },
                isError = amount.value.toDoubleOrNull() == null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )

            PaidToView(paidTo = paidTo)

            CategoryView(categories = categories)

            DateTimePickerView(
                timeInMillis = time.value,
                onTimeUpdate = { value -> time.value = value },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}