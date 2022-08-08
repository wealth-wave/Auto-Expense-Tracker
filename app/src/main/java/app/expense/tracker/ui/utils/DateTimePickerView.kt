package app.expense.tracker.ui.views.expense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat

@Composable
fun DateTimePickerView(
    timeInMillis: Long,
    onTimeUpdate: (time: Long) -> Unit,
    modifier: Modifier = Modifier
) {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { timePicker, minute, second ->
            calendar.set(Calendar.HOUR, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            onTimeUpdate(calendar.timeInMillis)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )
            val datePickerDialog = DatePickerDialog(
                LocalContext.current,
                { datePicker, p1, p2, p3 ->
                    calendar.set(Calendar.YEAR, datePicker.year)
                    calendar.set(Calendar.MONTH, datePicker.month)
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                    timePickerDialog.show()
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
        