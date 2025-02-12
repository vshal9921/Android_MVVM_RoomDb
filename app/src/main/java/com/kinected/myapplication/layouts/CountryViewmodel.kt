package com.kinected.myapplication.layouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.kinected.myapplication.common.WorkerUtil
import com.kinected.myapplication.data.CountryRepo
import com.kinected.myapplication.data.CountryResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewmodel @Inject constructor(
    private val countryRepo: CountryRepo
) : ViewModel() {

    val countryList :StateFlow<List<CountryResponseItem>> = countryRepo.countryList

    init {


        /*viewModelScope.launch {
            countryRepo.fetchCountryList()
        }*/
    }
}