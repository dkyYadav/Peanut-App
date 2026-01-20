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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peanut.Presentation.viewmodel.OpenTradeViewModel
import com.example.peanut.UiState
import com.example.peanut.domain.Model.OpenTradeResponse
import androidx.compose.material3.Divider
import androidx.compose.ui.Alignment
import com.example.peanut.Presentation.viewmodel.AccountInfoViewModel


@Composable
fun HistoryScreen(
    innerPadding: PaddingValues,
    openTradeViewModel: OpenTradeViewModel,
    accountInfoViewModel: AccountInfoViewModel
) {
    val openTreadState by openTradeViewModel.openTreadInfo.collectAsState()

    when(openTreadState){
        is UiState.Failure -> {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "No Internet Connection",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    openTradeViewModel.refetchData()
                    accountInfoViewModel.reloadData()
                }) {
                    Text("Reload")
                }
            }
        }
        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success ->{
            val data = (openTreadState as UiState.Success<List<OpenTradeResponse>>).data
            OpenTradesScreen(data,innerPadding,openTradeViewModel)
        }
        is UiState.Idle -> {}
    }
}
@Composable
fun OpenTradesScreen(
    trades: List<OpenTradeResponse>,
    innerpadding: PaddingValues,
    openTradeViewModel: OpenTradeViewModel) {

    // Calculate the profit
    val totalProfit = trades.sumOf { it.profit }
    val profitColor = if (totalProfit >= 0) {
        Color.Green
    }else{
        Color.Red
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Total Profit
        item {
            Card(
                modifier = Modifier
                .fillMaxWidth().padding(10.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement =Arrangement.SpaceEvenly
                ){
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total Profit",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = String.format("%.2f", totalProfit),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = profitColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Open Trades: ${trades.size}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.wrapContentSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Button(onClick = {
                            openTradeViewModel.refetchData()
                        }) {
                            Text(
                                text = "Reload"
                            )
                        }
                    }
                }
            }
        }
        items (trades){trade->
            TradeCard(trade)
        }
    }
}

@Composable
fun TradeCard(trade: OpenTradeResponse) {

    val profitColor = if (trade.profit >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
    val tradeType = if (trade.type == 0) "BUY" else "SELL"
    val typeColor = if (trade.type == 0) Color(0xFF1565C0) else Color(0xFFD32F2F)


    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // 🔹 Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = trade.symbol,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tradeType,
                        color = typeColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                // Profit And LLoss Amount
                Text(
                    text = String.format("%.2f", trade.profit),
                    color = profitColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Prices Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceItem("Open", trade.openPrice)
                PriceItem("Current", trade.currentPrice)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 🔹 Bottom Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Vol: ${trade.volume}")
                Text(text = "Ticket: ${trade.ticket}")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Opened: ${trade.openTime}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun PriceItem(label: String, price: Double) {
    Column {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(
            text = price.toString(),
            fontWeight = FontWeight.SemiBold
        )
    }
}
