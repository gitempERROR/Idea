package com.example.idea.view.MainActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.domain.MainViewModel
import com.example.idea.model.IdeaData
import com.example.idea.view.ui.theme.IdeaTheme

@Composable
fun Main(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()){
    mainViewModel.subscribeToData()
    val ideasList = mainViewModel.ideasList.collectAsState()

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
                Image(
                    ImageVector.vectorResource(R.drawable.idea_user),
                    contentDescription = "",
                    modifier = Modifier.fillMaxHeight().align(alignment = Alignment.BottomStart).scale(0.8f)
                )
                Image(
                    ImageVector.vectorResource(R.drawable.ideas_my),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize().scale(0.6f)
                )
            }
            LazyColumn(
                modifier = Modifier.weight(0.75f, fill = true).padding(horizontal = 10.dp)
            ) {
                items(ideasList.value) {
                    item ->
                    IdeaElement(item, mainViewModel.icons[item.status - 1]) {}
                }
            }
            Box(
                modifier = Modifier.weight(0.15f, fill = true),
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
                        .scale(15f)
                )
            }
        }
    }
}

