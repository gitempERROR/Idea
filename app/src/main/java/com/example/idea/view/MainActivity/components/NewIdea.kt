package com.example.idea.view.MainActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.domain.NewIdeaViewModel
import com.example.idea.view.ui.theme.IdeaTheme
import com.example.idea.view.ui.theme.MultiLineTextField
import com.example.idea.view.ui.theme.OneLineTextField

@Composable
fun NewIdea(
    navController: NavHostController,
    newIdeaViewModel: NewIdeaViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = newIdeaViewModel.navigationStateFlow) {
        newIdeaViewModel.navigationStateFlow.collect { event ->
            event?.let { navController.navigate(event.route) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = IdeaTheme.colors.tertiary)
    ) {
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
                Image(
                    ImageVector.vectorResource(R.drawable.describe_idea),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(0.8f)
                )
            }
            Column(
                modifier = Modifier.weight(0.75f, fill = true)
            ) {
                OneLineTextField(
                    value = newIdeaViewModel.editedIdea.name,
                    input = { newValue ->
                        newIdeaViewModel.updateEditedIdea(
                            newIdeaViewModel.editedIdea.copy(
                                name = newValue
                            )
                        )
                    },
                    modifier = Modifier.padding(horizontal = 20.dp),
                    textStyle = IdeaTheme.typography.labelSmall,
                    indicatorY = 60f,
                    borderStroke = 0.dp
                )
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(17.dp))
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
                MultiLineTextField(
                    value = newIdeaViewModel.editedIdea.description,
                    input = { newValue ->
                        newIdeaViewModel.updateEditedIdea(
                            newIdeaViewModel.editedIdea.copy(
                                description = newValue
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxHeight(),
                    borderStroke = 0.dp
                )
            }
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
            ) {
                Button(
                    onClick = { newIdeaViewModel.navigateToMain() },
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
                Button(
                    onClick = {
                        newIdeaViewModel.addIdea()
                        newIdeaViewModel.navigateToMain()
                    },
                    modifier = Modifier
                        .size(150.dp)
                        .align(alignment = Alignment.TopEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    enabled = newIdeaViewModel.isButtonEnabled.value
                ) {
                    Image(
                        if (newIdeaViewModel.isButtonEnabled.value)
                            ImageBitmap.imageResource(R.drawable.ready_on)
                        else
                            ImageBitmap.imageResource(R.drawable.ready_off),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}