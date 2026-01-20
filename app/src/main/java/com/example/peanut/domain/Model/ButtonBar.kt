package com.example.peanut.domain.Model


import androidx.annotation.DrawableRes
import com.example.peanut.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

data class NavItem(
    val label : String,
    val icon : ImageVector? = null,
    @DrawableRes val drawableIcon: Int? = null
)

val navItemList = listOf(
    NavItem(
        "Home",
        Icons.Default.Home
    ),
    NavItem(
        "Treads",
        drawableIcon = R.drawable.ic_history
    ),
    NavItem(
        "Profile",
        Icons.Default.AccountCircle)
)