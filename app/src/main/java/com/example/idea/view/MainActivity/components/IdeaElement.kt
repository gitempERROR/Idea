package com.example.idea.view.MainActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.idea.model.IdeaData
import com.example.idea.view.ui.theme.IdeaTheme

@Composable
fun IdeaElement(item: IdeaData, image: String, onClick: () -> Unit) {
    Spacer(
        modifier = Modifier.size(15.dp)
    )
    Row(
        modifier = Modifier
            .background(
                color = IdeaTheme.colors.tertiary,
                shape = RoundedCornerShape(50)
            )
            .border(
                6.dp,
                color = if (item.status in listOf(
                        1,
                        3
                    )
                ) IdeaTheme.colors.primary else IdeaTheme.colors.onBackground,
                shape = RoundedCornerShape(50),
            )
            .fillMaxWidth()
            .padding(10.dp)
            .height(45.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = item.name,
            style = IdeaTheme.typography.labelSmall,
            color = IdeaTheme.colors.secondary,
            modifier = Modifier
                .padding(5.dp)
                .weight(0.8f)
                .align(Alignment.CenterVertically),
            overflow = TextOverflow.Ellipsis
        )
        val imageState = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL)
                .build()
        ).state
        if (imageState is AsyncImagePainter.State.Success) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Image(
                    painter = imageState.painter,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
