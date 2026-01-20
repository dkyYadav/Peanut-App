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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel
import com.example.peanut.Presentation.viewmodel.AuthViewModel
import com.example.peanut.Presentation.viewmodel.OpenTradeViewModel
import com.example.peanut.UiState
import com.example.peanut.domain.Model.AccountInfoResponse
import com.example.peanut.domain.Model.PhoneResponse

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    accountInfoViewModel: AccountInfoViewModel,
    innerPadding: PaddingValues,
    openTradeViewModel: OpenTradeViewModel
) {

    val accountInfoState by accountInfoViewModel.accountInfo.collectAsState()
    val phoneNoInfoState by accountInfoViewModel.phoneNoInfo.collectAsState()

    when {
        accountInfoState is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        accountInfoState is UiState.Success && phoneNoInfoState is UiState.Success -> {
            val data = (accountInfoState as UiState.Success<AccountInfoResponse>).data
            val phoneNo = (phoneNoInfoState as UiState.Success<PhoneResponse>).data

            ProfileContent(data, phoneNo, innerPadding, authViewModel)
        }

        accountInfoState is UiState.Failure || phoneNoInfoState is UiState.Failure -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Internet Connection",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            accountInfoViewModel.reloadData()
                            openTradeViewModel.refetchData()
                                  },
                        modifier = Modifier.widthIn(max = 200.dp)
                    ) {
                        Text("Retry")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { authViewModel.logout() },
                        modifier = Modifier.widthIn(max = 200.dp)
                    ) {
                        Text("Logout")
                    }
                }
            }
        }

        else -> {}
    }
}

@Composable
fun ProfileContent(
    account: AccountInfoResponse,
    phoneNO: PhoneResponse,
    innerPadding: PaddingValues,
    authViewModel: AuthViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 8.dp,
            bottom = innerPadding.calculateBottomPadding() + 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileHeader(account, phoneNO)
        }

        item {
            FinancialSection(account)
        }

        item {
            AccountDetailsSection(account)
        }

        item {
            Button(
                onClick = { authViewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 400.dp)
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun ProfileHeader(account: AccountInfoResponse, phoneNO: PhoneResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp), // Max width for tablets
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow("User Name", account.name.toString())
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Phone NO", phoneNO.lastFourDigits.toString())
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${account.city}, ${account.country}",
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FinancialSection(account: AccountInfoResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Financial Information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
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
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Account Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Address", account.address)
            InfoRow("Zip Code", account.zipCode)
            InfoRow("Phone", account.phone)
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
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}