package app.expense.tracker.ui.views.expense

import CategorySelectorView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.expense.presentation.viewModels.AddExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    suggestionId: Long? = null,
    expenseId: Long? = null,
    addExpenseViewModel: AddExpenseViewModel = hiltViewModel()
) {

    //TODO call expense Viewmodel to fetch data

    val amount = rememberSaveable() { mutableStateOf("") }
    val paidTo = rememberSaveable() { mutableStateOf("") }
    val category = rememberSaveable() { mutableStateOf("") }
    val time = rememberSaveable() { mutableStateOf(System.currentTimeMillis()) }

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
                    IconButton(onClick = {
                        //Call ViewModel
                    }) {
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                label = { Text(text = "Paid To") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = paidTo.value,
                onValueChange = { value ->
                    paidTo.value = value
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            CategorySelectorView(
                category = category.value,
                categories = emptyList(),
                onCategoryEntered = { value ->
                    category.value = value
                    //TODO perform API Call
                },
                onCategorySelect = { value ->
                    category.value = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

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