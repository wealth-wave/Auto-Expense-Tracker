package app.expense.tracker.ui.views.addExpense

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.expense.tracker.R
import app.expense.tracker.ui.utils.CategoryInputDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryView(
    categories: MutableState<List<String>>
) {
    val categoryDialogOpen = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.default_padding),
                end = dimensionResource(id = R.dimen.default_padding),
                top = dimensionResource(id = R.dimen.default_padding)
            )
        ) {
            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { categoryDialogOpen.value = true }) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = stringResource(R.string.add_category)
                )
            }

            if (categoryDialogOpen.value) {
                CategoryInputDialog(onCategoryEntered = { category ->
                    categories.value = categories.value.toMutableList().apply {
                        add(0, category)
                    }
                    categoryDialogOpen.value = false
                }, onDismiss = { categoryDialogOpen.value = false })
            }
        }

        if (categories.value.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                categories.value.filter { it.isNotBlank() }.forEach { item ->
                    InputChip(
                        modifier = Modifier.padding(end = 4.dp),
                        selected = true,
                        label = { Text(text = item, style = MaterialTheme.typography.labelLarge) },
                        onClick = {
                            categories.value =
                                categories.value.toMutableList().apply { remove(item) }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(id = R.string.remove_category)
                            )
                        }
                    )
                }
            }
        }
    }
}
