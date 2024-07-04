package app.expense.tracker.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import app.expense.tracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountInputDialog(
    amount: Double,
    onAmountEntered: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val amountState = remember { mutableStateOf(if (amount > 0) amount.toString() else "") }
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
            Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                Column {
                    TextField(
                        label = { Text(text = stringResource(R.string.amount)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = amountState.value,
                        onValueChange = { value ->
                            amountState.value = value
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                        ),
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                    Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                        TextButton(
                            enabled = amountState.value.toDoubleOrNull() != null,
                            onClick = {
                                onAmountEntered(
                                    amountState.value.toDoubleOrNull() ?: 0.0
                                )
                            }
                        ) {
                            Text(text = stringResource(id = R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}
