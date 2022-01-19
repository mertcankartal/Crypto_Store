package com.example.composecrypto.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.composecrypto.model.Crypto
import com.example.composecrypto.model.CryptoDetail
import com.example.composecrypto.model.CryptoDetailItem
import com.example.composecrypto.util.Resource
import com.example.composecrypto.viewmodel.DetailScreenViewModel

@Composable
fun DetailScreen(
    id: String,
    price: String,
    navController: NavController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    /*
    var cryptoItem by remember { mutableStateOf<Resource<CryptoDetailItem>>(Resource.Loading()) }

    LaunchedEffect(key1 = Unit) {
        cryptoItem = viewModel.getCryptoDetail(id)
    }
*/
    //produceState ile yapılabilir.Üstte de launchedeffect ile yapımı var.ikiside ok.
    val cryptoItem by produceState<Resource<CryptoDetail>>(initialValue = Resource.Loading())
    {
        value = viewModel.getCryptoDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when (cryptoItem) {

                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]
                    Text(
                        text = selectedCrypto.name,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = rememberImagePainter(data = selectedCrypto.logo_url),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(
                        text = price,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Center

                    )

                }

                is Resource.Error -> {
                    Text(cryptoItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }

            }
        }
    }
}