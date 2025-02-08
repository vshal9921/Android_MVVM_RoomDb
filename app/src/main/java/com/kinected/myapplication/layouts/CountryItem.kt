package com.kinected.myapplication.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CountryItem(imageUrl: String, name: String) {

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ){

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "image",
            modifier = Modifier.size(38.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = name
        )
    }
}

@Preview(showBackground = true)
@Composable
fun  PreviewCountryItem() {
    CountryItem(imageUrl = "", name = "India")
}