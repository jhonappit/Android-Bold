package com.jhonjto.android_bold.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jhonjto.android_bold.R
import com.jhonjto.android_bold.ui.theme.Blue7EA1C4
import kotlinx.coroutines.delay
import navigation.NavigationItem

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate(NavigationItem.SEARCH.route)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Blue7EA1C4
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(id = R.string.weather_app),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}
