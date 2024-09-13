package com.example.idea.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import com.example.idea.domain.Login
import com.example.idea.view.MainActivity.theme.IdeaTheme
import com.example.idea.view.MainActivity.theme.MainButton
import com.example.idea.view.MainActivity.theme.OneLineTextField

@Composable
fun Login(navController: NavHostController, loginViewModel: Login = hiltViewModel()) {

    val transition = rememberInfiniteTransition("")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 360.dp)
        ) {
            Image(
                ImageBitmap.imageResource(R.drawable.idea_lamp_main),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(4.5f)
                    .graphicsLayer { rotationZ = rotation }
                )
            Image(
                ImageBitmap.imageResource(R.drawable.idea_lamp_back),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.5f)
                    .padding(start = 5.dp)
            )
        }

        Image(
            ImageVector.vectorResource(R.drawable.idea_logo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 85.dp, end = 85.dp),
        )
        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(bottom = 95.dp)
        ) {
            Text(
                text = "Логин",
                color = IdeaTheme.colors.secondary,
                style = IdeaTheme.typography.labelMedium,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            OneLineTextField(
                value = loginViewModel.loginState.login,
                input = {newValue -> loginViewModel.updateLoginState(loginViewModel.loginState.copy(login = newValue))},
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Text(
                text = "Пароль",
                color = IdeaTheme.colors.secondary,
                style = IdeaTheme.typography.labelMedium,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            OneLineTextField(
                value = loginViewModel.loginState.password,
                input = {newValue -> loginViewModel.updateLoginState(loginViewModel.loginState.copy(password = newValue))},
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(
                modifier = Modifier.height(35.dp)
            )
            MainButton(
                text = "Войти",
                enabled = loginViewModel.isButtonEnabled,
                onClick = {},
                modifier = Modifier.padding(horizontal = 30.dp).height(60.dp)
            )
        }
    }
}