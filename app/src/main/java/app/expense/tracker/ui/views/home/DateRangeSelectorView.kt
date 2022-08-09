package app.expense.tracker.ui.views.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.expense.presentation.viewStates.DateRange

@ExperimentalMaterial3Api
@Composable
fun DateRangeSelectorView(
    modifier: Modifier = Modifier,
    onRangeSelect: (DateRange) -> Unit
) {

    val options = listOf(
        Pair("This Month", DateRange.ThisMonth),
        Pair("Last 30 Days", DateRange.Last30Days),
        Pair("Previous Month", DateRange.LastMonth)
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        AssistChip(
            onClick = { },
            label = {
                Text(text = selectedOption.first, style = MaterialTheme.typography.labelSmall)
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Range")
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.first) },
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                        onRangeSelect(selectionOption.second)
                    }
                )
            }
        }
    }
}
