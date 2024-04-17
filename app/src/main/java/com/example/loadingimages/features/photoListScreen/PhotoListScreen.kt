package com.example.loadingimages.features.photoListScreen

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.loadingimages.R
import com.example.loadingimages.core.ConnectivityObserver
import com.example.loadingimages.core.InfoDialog
import com.example.loadingimages.core.ListState
import com.example.loadingimages.core.NetworkConnectivityObserver
import com.example.loadingimages.core.collectSideEffect
import com.example.loadingimages.core.isInternetAvailable
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun PhotoListScreen() {
    val viewModel = koinViewModel<PhotoListScreenViewModel>()
    val state = viewModel.uiState.collectAsState()
    val lazyColumnListState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var stateIsConnected by remember {
        mutableStateOf(false)
    }
    val shouldStartPaginate = remember {
        derivedStateOf {
            state.value.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }
    if (state.value.context == null) {
        viewModel.sendIntent(PhotoListIntent.SetContext(context))
    }
    lateinit var connectivityObserver: ConnectivityObserver
    connectivityObserver = NetworkConnectivityObserver(context = context)

    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
    when (status) {
        ConnectivityObserver.Status.Available -> {
            if (stateIsConnected) {
                viewModel.setPaginateValues()
                stateIsConnected = false
            }

        }

        ConnectivityObserver.Status.Lost -> {
            stateIsConnected = true
        }

        ConnectivityObserver.Status.Losing -> {
            // stateIsConnected = true
        }

        ConnectivityObserver.Status.Unavailable -> {
            stateIsConnected = true
            if(state.value.response.isEmpty() && !isInternetAvailable(context = context)){
                InfoDialog(title = stringResource(R.string.network_error), desc = stringResource(R.string.please_check_your_internet_connectivity)) {
                }
            }

        }
    }
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && state.value.listState == ListState.IDLE || state.value.listState == ListState.PAGINATION_EXHAUST)
            viewModel.sendIntent(PhotoListIntent.Init)
    }


    viewModel.collectSideEffect {
        when (it) {
            is PhotoListSideEffect.ShowApiError -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


    Scaffold(containerColor = Color.White) {
        Column(modifier = Modifier.padding(it)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = lazyColumnListState,
                modifier = Modifier.padding(it)
            ) {
                items(items = state.value.response) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 16.dp)

                            .height(160.dp), shape = RoundedCornerShape(10.dp)
                    ) {

                        val bitmap = BitmapFactory.decodeFile(it.imageUrl)
                        bitmap?.let { bitmap1 ->
                            Image(
                                bitmap = bitmap1.asImageBitmap(),
                                contentDescription = it.name,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                item(span = {
                    GridItemSpan(2)
                }) {
                    when (state.value.listState) {
                        ListState.LOADING -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Refresh Loading"
                                    , color = Color.Black
                                )

                                CircularProgressIndicator(color = Color.Black)
                            }
                        }

                        ListState.PAGINATING -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(text = "Loading", color = Color.Black)
                                CircularProgressIndicator(color = Color.Black)
                            }
                        }

                        ListState.PAGINATION_EXHAUST -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp, vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(imageVector = Icons.Rounded.Face, contentDescription = "")

                                Text(text = stringResource(R.string.nothing_left),color = Color.Black)

                                TextButton(
                                    modifier = Modifier
                                        .padding(top = 8.dp),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp
                                    ),
                                    onClick = {
                                        coroutineScope.launch {
                                            lazyColumnListState.animateScrollToItem(0)
                                        }
                                    },
                                    content = {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                                contentDescription = ""
                                            )
                                            Text(text = stringResource(R.string.back_to_top),color = Color.Black)
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }


}