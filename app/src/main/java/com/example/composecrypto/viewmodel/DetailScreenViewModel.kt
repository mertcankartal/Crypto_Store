package com.example.composecrypto.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composecrypto.model.CryptoDetail
import com.example.composecrypto.model.CryptoDetailItem
import com.example.composecrypto.repository.CryptoRepository
import com.example.composecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private var repository: CryptoRepository) :
    ViewModel() {

    suspend fun getCryptoDetail(id: String): Resource<CryptoDetail> {
        return repository.getCryptoDetail(id)
    }

}