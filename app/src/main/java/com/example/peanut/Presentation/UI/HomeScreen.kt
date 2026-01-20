package com.example.peanut.Presentation.UI

import android.R
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.Presentation.viewmodel.OpenTradeViewModel
import com.example.peanut.data.local.Preferences.AuthPreferences
import com.example.peanut.domain.Model.navItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  Home(
    authViewModel: AuthViewModel,
    accountInfoViewModel: AccountInfoViewModel,
    preferences: AuthPreferences,
    openTradeViewModel: OpenTradeViewModel
    ) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(Unit){
        Log.d("ApiCall", " ViewModel Starting.. ")
        val login = preferences.getLogin()
        val token = preferences.getToken()
        Log.d("ApiCall", "Get Data From SharedPreferences login=$login token=$token")

        if (login.isNullOrEmpty() || token.isNullOrEmpty()) {
            Log.e("ApiCall", "Login or token missing")
            Toast.makeText(context, "Session Expired", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect // Exit early - don't proceed with API calls
        }
        accountInfoViewModel.fetchAccountInfo(login.toString(), token.toString())
        accountInfoViewModel.fetchPhoneNo(login.toString(),token.toString())
        openTradeViewModel.fetchOpenTread(login.toString(), token.toString())
        accountInfoViewModel.reloadData()

    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Peanut",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu click */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

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
            1-> HistoryScreen(innerpadding,openTradeViewModel,accountInfoViewModel)
            2-> ProfileScreen(
                authViewModel,
                accountInfoViewModel,
                innerpadding,
                openTradeViewModel
            )
        }
    }
}
@Composable
fun HomeScreen(
    innerpadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = innerpadding.calculateTopPadding() + 16.dp,
            bottom = innerpadding.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Section
        item {
            WelcomeCard()
        }
        // Market Overview
        item {
            MarketOverviewCard()
        }
        item {
            TradingTipsCard()
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF8656E0)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Welcome Back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ready to trade today?",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}


@Composable
fun MarketOverviewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Market Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            MarketItem("EUR/USD", "1.0876", "+0.12%", true)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            MarketItem("GBP/USD", "1.2654", "-0.08%", false)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            MarketItem("USD/JPY", "148.32", "+0.24%", true)
        }
    }
}


@Composable
fun MarketItem(pair: String, price: String, change: String, isPositive: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = pair,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = price,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        Text(
            text = change,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)
        )
    }
}


@Composable
fun TradingTipsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💡",
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Trading Tip",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Always use stop-loss to manage your risk effectively.",
                    fontSize = 13.sp,
                    color = Color(0xFF5D4037)
                )
            }
        }
    }
}
