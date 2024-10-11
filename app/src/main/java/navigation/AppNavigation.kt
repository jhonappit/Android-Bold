package navigation

enum class Screen {
    SPLASH,
    SEARCH,
}
sealed class NavigationItem(val route: String) {
    object SPLASH : NavigationItem(Screen.SPLASH.name)
    object SEARCH : NavigationItem(Screen.SEARCH.name)
}
