package com.example.idea.view.MainActivity.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.domain.MainViewModel
import com.example.idea.view.MainActivity.navigation.Routes
import com.example.idea.view.ui.theme.IdeaTheme

@Composable
fun Main(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    mainViewModel.subscribeToData()
    val ideasList = mainViewModel.ideasList.collectAsState()
    val topBottomFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.05f to Color.Red,
        0.95f to Color.Red,
        1f to Color.Transparent
    )

    LaunchedEffect(key1 = mainViewModel.navigationStateFlow) {
        mainViewModel.navigationStateFlow.collect { event ->
            event?.let {
                if (event.route != Routes.Idea.route)
                    navController.navigate(event.route)
                else {
                    try {
                        navController.navigate(
                            "idea" +
                                    "/${mainViewModel.selectedIdea.name}" +
                                    "/${mainViewModel.selectedIdea.description}" +
                                    "/${mainViewModel.selectedIdea.status}" +
                                    "/${mainViewModel.selectedIdea.id}"
                        )
                    }
                    catch (e: Exception){
                        Log.e("navigation to idea", "Main: $e")
                    }
                }
            }
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(0.1f, fill = true)
            ) {
                Box(
                    modifier = Modifier.align(alignment = Alignment.BottomStart)
                ){
                    Image(
                        ImageVector.vectorResource(R.drawable.idea_user),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(alignment = Alignment.Center)
                            .scale(0.8f)
                            .clickable { mainViewModel.showExitButton() }
                    )
                    if (mainViewModel.showExit) {
                        Image(
                            ImageVector.vectorResource(R.drawable.exit),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(alignment = Alignment.Center)
                                .scale(0.9f)
                                .clickable { mainViewModel.navigateToLogin() }
                        )
                    }
                }
                Image(
                    ImageVector.vectorResource(R.drawable.ideas_my),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(0.6f)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(0.8f, fill = true)
                    .padding(horizontal = 10.dp)
                    .fadingEdge(topBottomFade)
            ) {
                items(ideasList.value) { item ->
                    IdeaElement(item, mainViewModel.icons[item.status - 1]) {
                        mainViewModel.navigateToIdea(item)
                    }
                }
            }
            Spacer(Modifier.weight(0.1f, fill = true))
        }
        if (mainViewModel.userRole.role_id == 1) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(alignment = Alignment.BottomCenter),
            ) {
                Button(
                    onClick = { mainViewModel.navigateToNewIdea() },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.Center),
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
                        .scale(15f)
                )
            }
        }
    }
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
