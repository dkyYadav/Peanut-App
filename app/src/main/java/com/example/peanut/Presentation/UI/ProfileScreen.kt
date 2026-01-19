package com.example.peanut.Presentation.UI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.UiState
import com.example.peanut.domain.Model.AccountInfoResponse

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    accountInfoViewModel: AccountInfoViewModel,
    innerpadding: PaddingValues
) {

    val accountInfoState by accountInfoViewModel.accountInfo.collectAsState()
    when(accountInfoState){
        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success ->{
            val data = (accountInfoState as UiState.Success<AccountInfoResponse>).data
            ProfileContent(data,innerpadding)
        }
        is UiState.Failure -> {
            Text("Something went wrong")
        }

        else -> {}
    }
}

@Composable
fun ProfileContent(account: AccountInfoResponse, innerpadding: PaddingValues) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)
    ) {

        item {
            ProfileHeader(account)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            FinancialSection(account)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            AccountDetailsSection(account)

        }
    }
}

@Composable
fun ProfileHeader(account: AccountInfoResponse) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            val damage = null
            Text(
                text = account.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${account.city}, ${account.country}",
                color = Color.Gray
            )
        }
    }
}
@Composable
fun FinancialSection(account: AccountInfoResponse) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Financial Information",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Balance", account.balance.toString())
            InfoRow("Equity", account.equity.toString())
            InfoRow("Free Margin", account.freeMargin.toString())
            InfoRow("Leverage", "1:${account.leverage}")
        }
    }
}

@Composable
fun AccountDetailsSection(account: AccountInfoResponse) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Account Details",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Address", account.address)
            InfoRow("Zip Code", account.zipCode)
            InfoRow("Phone", account.phone)
            //InfoRow("Swap Free", if (account.isSwapFree) "Yes" else "No")
            //InfoRow("Open Trades", if (account.isAnyOpenTrades) "Yes" else "No")
            InfoRow("Total Trades", account.totalTradesCount.toString())
            InfoRow("Total Volume", account.totalTradesVolume.toString())
        }
    }
}
@Composable
fun InfoRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontWeight = FontWeight.Medium)
        Text(text = value)
    }
}
