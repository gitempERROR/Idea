package com.example.idea.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.domain.Splash
import com.example.idea.view.MainActivity.theme.IdeaTheme

@Composable
fun Splash(navController: NavHostController, splashViewModel: Splash = hiltViewModel()) {

    splashViewModel.launch()
    LaunchedEffect(key1 = splashViewModel.navigationStateFlow) {
        splashViewModel.navigationStateFlow.collect { event ->
            event?.let { navController.navigate(event.route) }
        }
    }

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
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            ImageVector.vectorResource(R.drawable.idea_logo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
        )
        Image(
            ImageBitmap.imageResource(R.drawable.idea_lamp_full),
            contentDescription = "",
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .scale(4.5f)
                .padding(top = 40.dp),
        )
    }
}
