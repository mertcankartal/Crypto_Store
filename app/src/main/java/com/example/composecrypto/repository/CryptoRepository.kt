package com.example.composecrypto.repository

import com.example.composecrypto.model.Crypto
import com.example.composecrypto.model.CryptoDetail
import com.example.composecrypto.model.CryptoDetailItem
import com.example.composecrypto.service.CryptoAPI
import com.example.composecrypto.util.API_KEY
import com.example.composecrypto.util.Attributes
import com.example.composecrypto.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api : CryptoAPI
) {
    suspend fun getCryptoList():Resource<Crypto>{
        val response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCryptoDetail(id:String):Resource<CryptoDetail>{
        val response = try {
            api.getCryptoDetail(API_KEY,id, Attributes)
        }catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}