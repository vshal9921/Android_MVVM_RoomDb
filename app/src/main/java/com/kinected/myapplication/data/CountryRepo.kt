package com.kinected.myapplication.data

import android.util.Log
import com.kinected.myapplication.common.PreferenceRepo
import com.kinected.myapplication.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryRepo @Inject constructor(
    private val apiService: ApiService,
    private val countryDao: CountryDao,
    private val preferenceRepo: PreferenceRepo
){

    private val _countryList = MutableStateFlow<List<CountryResponseItem>>(emptyList())
    var countryList : StateFlow<List<CountryResponseItem>> = _countryList.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val API_CALL_INTERVAL = 15 * 60 * 1000L // 15 minutes in milliseconds
    }

    init {
        startBackgroundApiCall()
    }

    private fun setNewCountryList(newList: List<CountryResponseItem>){

        _countryList.value = newList

        // save data in local db
        coroutineScope.launch(Dispatchers.IO){

        preferenceRepo.saveLastUpdateTime(System.currentTimeMillis())

            countryDao.deleteAllList()

        countryList.value.forEach { item ->
                countryDao.insertCountry(item)

            }
        }

    }

    fun fetchCountryList() {
        coroutineScope.launch(Dispatchers.IO) {
            val lastApiCallTime = preferenceRepo.getLastUpdateTime() ?: 0
            val currentTime = System.currentTimeMillis()

            Log.d("Last_time", "qwerty last time = $lastApiCallTime")
            Log.d("Last_time", "qwerty diff = ${currentTime - lastApiCallTime}")

            if (currentTime - lastApiCallTime >= API_CALL_INTERVAL) {
                getCountryListFromApi()
            } else {
                loadFromDatabase()
            }
        }
    }

    private suspend fun loadFromDatabase() {
        val localData = countryDao.getAllCountries()
        _countryList.value = localData
    }

    fun getCountryListFromApi(){

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getCountryList()
                setNewCountryList(response)
            } catch (e: Exception) {
                e.printStackTrace()
                loadFromDatabase() // Load from local DB in case of failure
            }
        }
    }

    private fun startBackgroundApiCall() {
        coroutineScope.launch(Dispatchers.IO) {
            while (true) {

                delay(API_CALL_INTERVAL)

                try {
                    getCountryListFromApi()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}