package com.example.composecrypto.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.composecrypto.model.CryptoItem
import com.example.composecrypto.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                text = "Crypto Store",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                hint = "Search Crypto ",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                //viewmodel işlemleri
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(20.dp))
            CryptoListViewModel(navController = navController)
        }

    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(hint != "") }

    Box(modifier = modifier) {
        BasicTextField(
            value = text, onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(7.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(20.dp, 16.dp)
                .onFocusChanged {
                    //kullanıcı tıklamayı bırakınca olacaklar
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                })
        if (isHintDisplayed) {
            Text(text = hint, color = Color.Black, modifier = Modifier.padding(20.dp, 16.dp))
        }
    }

}

@Composable
fun CryptoListViewModel(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val cryptoList by remember { viewModel.crypoList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    CryptoListView(navController = navController, cryptos = cryptoList)
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        if (isLoading){
            CircularProgressIndicator()
        }
        if (errorMessage.isNotEmpty()){
           RetryView(error = errorMessage) {
               viewModel.loadCryptos()
           }
        }
    }
    
}

@Composable
fun CryptoListView(navController: NavController, cryptos: List<CryptoItem>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }

}

@Composable
fun CryptoRow(navController: NavController, crypto: CryptoItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.secondary)
        .clickable {
            navController.navigate("detail_screen/${crypto.currency}/${crypto.price}")
        }) {

        Text(
            text = crypto.currency,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(4.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = crypto.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(4.dp),
            color = Color.Green,
            fontWeight = FontWeight.SemiBold
        )

    }

}

@Composable
fun RetryView(
    error:String,
    onRetry : () -> Unit
) {
    Column {
        Text(text = error, color = Color.Black, fontSize = 20.sp)
        Button(onClick = {
            onRetry
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}