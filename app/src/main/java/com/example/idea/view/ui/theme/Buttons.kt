package com.example.idea.view.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainButton(
    text: String,
    enabled: State<Boolean>,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled.value,
        colors = ButtonDefaults.buttonColors(
            containerColor = IdeaTheme.colors.primary,
            disabledContainerColor = IdeaTheme.colors.onBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .offset(y = 32.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            color = IdeaTheme.colors.tertiary,
            style = IdeaTheme.typography.labelMedium,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}
