package app.expense.tracker.ui.utils

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerView(
    timeInMillis: Long,
    onTimeUpdate: (time: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { datePicker, _, _, _ ->
            calendar.set(Calendar.YEAR, datePicker.year)
            calendar.set(Calendar.MONTH, datePicker.month)
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
            onTimeUpdate(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    TextField(
        readOnly = true,
        enabled = false,
        label = { Text(text = "Time") },
        modifier = modifier
            .clickable(onClick = {
                datePickerDialog.show()
            }),
        value = SimpleDateFormat.getDateTimeInstance().format(calendar.time).toString(),
        onValueChange = {
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}
