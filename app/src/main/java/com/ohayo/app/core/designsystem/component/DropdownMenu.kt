package com.ohayo.app.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ohayo.app.core.designsystem.icon.NiaIcons

/**
 * Created by Furuichi on 29/12/2022
 */

@Composable
fun <T> CustomDropdownMenuButton(
    items: List<T>,
    onItemClick: (item: T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    dismissOnItemClick: Boolean = true,
    text: @Composable () -> Unit,
    itemText: @Composable (item: T) -> Unit,
    itemLeadingIcon: @Composable ((item: T) -> Unit)? = null,
    itemTrailingIcon: @Composable ((item: T) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            enabled = enabled,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            border = BorderStroke(
                width = CustomDropdownMenuDefaults.DropdownMenuButtonBorderWidth,
                color = if (enabled) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(
                        alpha = CustomDropdownMenuDefaults.DisabledDropdownMenuButtonBorderAlpha
                    )
                }
            ),
            contentPadding = CustomDropdownMenuDefaults.DropdownMenuButtonContentPadding
        ) {
            CustomDropdownMenuButtonContent(
                text = text,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) {
                            NiaIcons.ArrowDropUp
                        } else {
                            NiaIcons.ArrowDropDown
                        },
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
fun CustomDropdownMenuButtonContent(
    text: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Box(
        Modifier
            .padding(
                end = if (trailingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                }
            )
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
            text()
        }
    }
    if (trailingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            trailingIcon()
        }
    }
}

@Composable
fun <T> CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<T>,
    onItemClick: (item: T) -> Unit,
    dismissOnItemClick: Boolean = true,
    itemText: @Composable (item: T) -> Unit,
    itemLeadingIcon: @Composable ((item: T) -> Unit)? = null,
    itemTrailingIcon: @Composable ((item: T) -> Unit)? = null
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { itemText(item) },
                onClick = {
                    onItemClick(item)
                    if (dismissOnItemClick) onDismissRequest()
                },
                leadingIcon = if (itemLeadingIcon != null) {
                    { itemLeadingIcon(item) }
                } else {
                    null
                },
                trailingIcon = if (itemTrailingIcon != null) {
                    { itemTrailingIcon(item) }
                } else {
                    null
                }
            )
        }
    }
}

object CustomDropdownMenuDefaults {
    // TODO: File bug
    // OutlinedButton border color doesn't respect disabled state by default
    const val DisabledDropdownMenuButtonBorderAlpha = 0.12f

    // TODO: File bug
    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val DropdownMenuButtonBorderWidth = 1.dp

    // TODO: File bug
    // Various default button padding values aren't exposed via ButtonDefaults
    val DropdownMenuButtonContentPadding =
        PaddingValues(
            start = 24.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 8.dp
        )
}

@ThemePreviews
@Composable
fun CustomDropdownMenuButtonPreview() {
    CustomDropdownMenuButton(
        items = listOf("1", "2", "3"),
        onItemClick = {

        },
        text = {
            Text(text = "Demo")
        },
        itemText = {
            Text(text = it)
        }
    )
}
