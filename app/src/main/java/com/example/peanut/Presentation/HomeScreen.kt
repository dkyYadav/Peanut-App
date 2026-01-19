package com.example.peanut.Presentation

import android.R
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.peanut.Presentation.UI.HistoryScreen
import com.example.peanut.Presentation.UI.ProfileScreen
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.domain.Model.navItemList

@Composable
fun  Home(authViewModel: AuthViewModel,
    accountInfoViewModel: AccountInfoViewModel,
    preferences: AuthPreferences
    ) {
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        Log.d("AccountVM", " ViewModel Starting.. ")
        val login = preferences.getLogin()
        val token = preferences.getToken()
        Log.d("AccountVM", "Get Data From SharedPreferences login=$login token=$token")
        accountInfoViewModel.fetchAccountInfo(login.toString(), token.toString())
        if (login.isNullOrEmpty() || token.isNullOrEmpty()) {
            Log.e("AccountVM","Login or token missing")
        }
    }

    Scaffold (
        bottomBar = {
            NavigationBar(
                contentColor = Color.White
            ){
                navItemList.forEachIndexed { index, NavItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index},
                        icon = {
                            when {
                                NavItem.icon != null -> {
                                    Icon(
                                        imageVector = NavItem.icon,
                                        contentDescription = "Icon",
                                        tint = colorResource(id = R.color.black)
                                    )
                                }
                                NavItem.drawableIcon != null->{
                                    Icon(
                                        painter = painterResource(NavItem.drawableIcon),
                                        contentDescription = NavItem.label
                                    )
                                }
                            }
                        },
                        label = {
                            Text(text = NavItem.label,
                                modifier = Modifier,
                                colorResource(id = R.color.darker_gray))
                        }
                    )
                }
            }
    }
    ){innerpadding->
        when(selectedIndex){
            0-> HomeScreen(innerpadding)
            1-> HistoryScreen(innerpadding)
            2-> ProfileScreen(
                authViewModel,
                accountInfoViewModel,
                innerpadding
            )
        }
    }
}

@Composable
fun HomeScreen(
    innerpadding: PaddingValues
) {
    Column(
        modifier = Modifier.padding(innerpadding)
    ){
        Text("Home Screen")
    }
}