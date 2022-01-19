package com.example.composecrypto.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecrypto.model.CryptoItem
import com.example.composecrypto.repository.CryptoRepository
import com.example.composecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var crypoList = mutableStateOf<List<CryptoItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var searchCryptoList = listOf<CryptoItem>()
    private var isSearchStarting = true


    init {
        loadCryptos()
    }

    fun searchCryptoList(query:String){
        var listToSearch = if (isSearchStarting){
            crypoList.value
        }else{
            searchCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                crypoList.value = searchCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency.contains(query.trim(),true)
            }

            if(isSearchStarting){
                searchCryptoList = crypoList.value
                isSearchStarting = false
            }

            crypoList.value = results
        }

    }

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()

            when (result) {
                is Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoItem ->
                        CryptoItem(cryptoItem.currency, cryptoItem.price)
                    } as List<CryptoItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    crypoList.value += cryptoItems
                }

                is Resource.Error -> {
                    isLoading.value = false
                    errorMessage.value = result.message!!
                }
            }
        }

    }


}