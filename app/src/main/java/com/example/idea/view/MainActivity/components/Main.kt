package com.example.idea.view.MainActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.view.ui.theme.IdeaTheme


@Composable
fun Main(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = IdeaTheme.colors.background,
            )
    ) {
        Image(
            ImageBitmap.imageResource(R.drawable.idea_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        LazyColumn {

        }
        Box(
            modifier = Modifier.align(alignment = Alignment.BottomCenter).size(200.dp),
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Image(
                    ImageBitmap.imageResource(R.drawable.plus_button),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Image(
                ImageBitmap.imageResource(R.drawable.idea_lamp_main),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(10f)
            )
        }
    }
}
