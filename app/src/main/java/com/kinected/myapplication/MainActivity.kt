package com.kinected.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kinected.myapplication.layouts.CountryItem
import com.kinected.myapplication.layouts.CountryViewmodel
import com.kinected.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countryViewmodel: CountryViewmodel by viewModels()

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    MainLayout(
                        modifier = Modifier.padding(innerPadding)
                        .background(Color.White),
                        countryViewmodel = countryViewmodel
                    )
                }
            }
        }
    }
}

@Composable
fun MainLayout(modifier: Modifier, countryViewmodel: CountryViewmodel){

    val countryList = countryViewmodel.countryList.collectAsState()

    LazyColumn {

        items(countryList.value){ countryItem ->
            CountryItem(imageUrl = countryItem.flagPNG ?: "", name = countryItem.name ?: "")
        }
    }
}