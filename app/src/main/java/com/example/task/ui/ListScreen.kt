package com.example.task.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.task.CompanyDatas
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val jsonString = remember {
        context.assets.open("companies.json").bufferedReader().use { it.readText() }
    }

    val gson = Gson()
    val companyListType = object : TypeToken<List<CompanyDatas>>() {}.type
    val companies: List<CompanyDatas> = gson.fromJson(jsonString, companyListType)

    LazyColumn(modifier = modifier) {
        items(companies) { company ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = CardDefaults.outlinedShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = company.name)
                    Text(text = "Food: ${company.food.joinToString(", ")}")
                    Text(text = "Likes: ${company.likes}")
                    Text(text = "Comments: ${company.comments}")
                    Text(text = "Percentage: ${company.percentage ?: "N/A"}")
                }
            }
        }
    }
}