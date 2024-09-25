package com.example.idea.view.MainActivity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.idea.R
import com.example.idea.domain.IdeaViewModel
import com.example.idea.view.ui.theme.IdeaTheme
import com.example.idea.view.ui.theme.MainButton
import com.example.idea.view.ui.theme.MultiLineTextField

@Composable
fun Idea(
    navController: NavHostController,
    ideaName: String,
    ideaDesc: String,
    ideaStatus: String,
    ideaId: String,
    ideaViewModel: IdeaViewModel = hiltViewModel()
) {
    ideaViewModel.getUserRole()
    val scrollStateHorizontal = rememberScrollState()

    val scrollStateVertical = rememberScrollState()
    LaunchedEffect(key1 = ideaViewModel.navigationStateFlow) {
        ideaViewModel.navigationStateFlow.collect { event ->
            event?.let { navController.navigate(event.route) }
        }
    }

    val imageState = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(ideaViewModel.icons[ideaStatus.toInt() - 1])
            .size(Size.ORIGINAL)
            .build()
    ).state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = IdeaTheme.colors.tertiary)
    )
    {
        Image(
            ImageBitmap.imageResource(R.drawable.rounds),
            contentDescription = "",
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .offset(x = 150.dp, y = (-150).dp)
                .scale(3f)
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(0.1f, fill = true)
            ) {
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = ideaName,
                        color = IdeaTheme.colors.secondary,
                        style = IdeaTheme.typography.labelMedium,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier.horizontalScroll(scrollStateHorizontal)
                    )
                }

            }
            Box(
                modifier = Modifier.weight(0.75f, fill = true)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 50.dp)
                            .height(6.dp)
                            .background(
                                color = IdeaTheme.colors.primary,
                                shape = RoundedCornerShape(50)
                            )
                            .fillMaxWidth()
                    )
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(17.dp))
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxHeight()
                    ) {
                        MultiLineTextField(
                            value = "",
                            input = {},
                            modifier = Modifier
                                .fillMaxHeight(),
                            borderStroke = 0.dp,
                            backgroundColor = IdeaTheme.colors.secondaryContainer,
                            enabled = false
                        )
                        Text(
                            text = ideaDesc,
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize()
                                .verticalScroll(scrollStateVertical),
                            style = IdeaTheme.typography.bodyMedium,
                            color = IdeaTheme.colors.secondary
                        )
                    }
                }
                if (imageState is AsyncImagePainter.State.Success) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .fillMaxHeight()
                            .padding(end = 50.dp, bottom = 15.dp)
                            .align(alignment = Alignment.BottomEnd)
                    ) {
                        Image(
                            painter = imageState.painter,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = IdeaTheme.colors.tertiary,
                                    shape = RoundedCornerShape(50)
                                ),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
            ) {
                Button(
                    onClick = { ideaViewModel.navigateToMain() },
                    modifier = Modifier
                        .size(150.dp)
                        .align(alignment = Alignment.TopStart),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Image(
                        ImageBitmap.imageResource(R.drawable.back),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                if (ideaViewModel.userRole.role_id == 2) {
                    Column(
                        modifier = Modifier
                            .padding(end = 20.dp, top = 20.dp, bottom = 20.dp)
                            .width(180.dp)
                            .align(alignment = Alignment.TopEnd)
                    ) {
                        MainButton(
                            text = "Принять",
                            enabled = ideaStatus == "1",
                            onClick = { ideaViewModel.updateIdeaStatus(3, ideaId) },
                            modifier = Modifier.weight(0.45f),
                            textStyle = IdeaTheme.typography.displayMedium
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                        MainButton(
                            text = "Отказ",
                            enabled = ideaStatus == "1",
                            onClick = { ideaViewModel.updateIdeaStatus(2, ideaId) },
                            modifier = Modifier.weight(0.45f),
                            textStyle = IdeaTheme.typography.displayMedium
                        )
                    }
                }
            }
        }
    }
}