package dev.jyotiraditya.echoir.presentation.navigation

import dev.jyotiraditya.echoir.R

data class NavigationItem(
    val route: String,
    val icon: Int,
    val label: Int
)

val navigationItems = listOf(
    NavigationItem(
        route = Route.Home.path,
        icon = R.drawable.ic_home,
        label = R.string.search
    ),
    NavigationItem(
        route = Route.Search.Main.path,
        icon = R.drawable.ic_search,
        label = R.string.search
    ),
    NavigationItem(
        route = Route.Settings.path,
        icon = R.drawable.ic_settings,
        label = R.string.settings
    )
)