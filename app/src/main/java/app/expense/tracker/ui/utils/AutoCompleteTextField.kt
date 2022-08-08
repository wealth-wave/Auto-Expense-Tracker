package app.expense.tracker.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@ExperimentalMaterial3Api
@Composable
fun AutoCompleteTextField(
    label: String,
    value: String,
    suggestions: List<String>,
    onCategoryEntered: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            label = { Text(text = label) },
            value = value,
            onValueChange = { value ->
                expanded = true
                onCategoryEntered(value)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded && suggestions.isNotEmpty(),
            onDismissRequest = { expanded = false },
        ) {
            suggestions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        expanded = false
                        onCategorySelect(selectionOption)
                    },
                )
            }
        }
    }
}
