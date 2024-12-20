package com.example.testtaskdemo.screens

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.testtaskdemo.data.setup.Response
import com.example.testtaskdemo.viewmodel.DataItemsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testtaskdemo.data.pojo.WeatherObj
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    val weatherViewModel: DataItemsViewModel = hiltViewModel()
    val weatherResult by weatherViewModel._weatherResult.collectAsState()
    var search by remember {
        mutableStateOf("")
    }
    Column {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
            searchText = search,
            onTextChange = {
                search = it
            },
            onSubmit = {
                if (search.isNotEmpty()) {
                    weatherViewModel.getWeather(search)
                }
            })
        handleResponse(weatherResult = weatherResult, weatherViewModel) { search = "" }
        if (weatherViewModel.getCurrent.value != null && search.isEmpty()) {
            LocalSaveCardData(weatherViewModel.getCurrent.value!!)
        } else {
            if (weatherResult.data?.location == null) {
                EmptyCardData()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSearchView(weatherResult: Response<WeatherObj>, saveItem: (WeatherObj?) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(130.dp)
            .padding(all = 16.dp)
            .wrapContentHeight(align = Alignment.Top),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 5.dp)
                .clickable { saveItem.invoke(weatherResult.data) },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .padding(end = 16.dp)
                ) {
                    Column {
                        Text(
                            text = "${weatherResult.data?.location?.name}",
                            color = Color.Black,
                            fontSize = 25.sp
                        )
                        Text(
                            text = "${weatherResult.data?.current?.temp}°C",
                            color = Color.Black,
                            fontSize = 25.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.size(size = 16.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {
                    AsyncImage(
                        model = "${weatherResult.data?.current?.condition?.icon}",
                        contentDescription = "Translated description of what the image contains"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyCardData() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ProfilePicture() composable
            // ProfileContent() composable
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                        .clickable(onClick = { }) /*question = "3 Bananas required"*/
                        .clip(shape = RoundedCornerShape(16.dp)),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No City Selected",
                            modifier = Modifier.padding(6.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        //...
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Please Search For A City",
                            modifier = Modifier.padding(1.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                        )
                        //...
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalSaveCardData(value: WeatherObj) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                        .clickable(onClick = { }) /*question = "3 Bananas required"*/
                        .clip(shape = RoundedCornerShape(16.dp)),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        ) {
                            AsyncImage(
                                model = "${value.current?.condition?.icon}",
                                contentDescription = "Translated description of what the image contains"
                            )
                        }
                        //...
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.location.name,
                            modifier = Modifier.padding(6.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        //...
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${value?.current?.temp}°C",
                            modifier = Modifier.padding(6.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        //...
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(130.dp)
                            .padding(all = 16.dp)
                            .wrapContentHeight(align = Alignment.Top),
                    ) {
                        Column {
                            Row(Modifier.padding(4.dp)) {

                                Text(
                                    text = "Humidity",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)

                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "UV",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)

                                        .padding(4.dp)

                                )

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Feels Like",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)

                                        .padding(4.dp)

                                )
                            }
                            Row(Modifier.padding(4.dp)) {

                                Text(
                                    text = "${value.current.humidity}%",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)

                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${value.current.uv}",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)

                                )

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${value.current.feelsLike}°",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)

                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    searchText: String,
    onTextChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    TextField(
        searchText, { onTextChange.invoke(it) },
        modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                shape = RoundedCornerShape(10.dp),
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
            .background(Color.White),
        placeholder = {
            Text(text = "Search", style = MaterialTheme.typography.bodyLarge)
        },
        trailingIcon = {
            Icon(Icons.Default.Search, contentDescription = "",
                modifier = Modifier.clickable { onSubmit.invoke() })

        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSubmit.invoke() }),
        singleLine = true
    )
}


@Composable
fun handleResponse(
    weatherResult: Response<WeatherObj>,
    weatherViewModel: DataItemsViewModel,
    clearSearch: () -> Unit
) {
    val activity = LocalContext.current as? Activity
    val coroutine = rememberCoroutineScope()
    when (weatherResult) {
        is Response.Success -> {
            if (weatherResult.data?.location?.name != null) {
                WeatherSearchView(weatherResult) {
                    it?.let {
                        coroutine.launch {
                            weatherViewModel.saveObject(it)
                            weatherViewModel.reset()
                            clearSearch.invoke()

                        }
                    }
                }
            }
        }

        is Response.Loading -> {
            Loader()
        }

        is Response.Error -> {
            Toast.makeText(activity, "${weatherResult.message}", Toast.LENGTH_SHORT).show()

        }

        else -> {}
    }
}

@Composable
fun Loader() {
}







