package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jhonjto.android_bold.presentation.search.SearchScreen
import com.jhonjto.android_bold.presentation.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.SPLASH.route,
    ) {
        composable(NavigationItem.SPLASH.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.SEARCH.route) {
            SearchScreen()
        }
    }
}