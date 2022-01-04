


import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.ye.compose.ui.AppViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.ye.compose.MainActivity
import com.ye.compose.databinding.WebViewBinding
import com.ye.compose.model.Slight
import android.webkit.GeolocationPermissions

import android.webkit.JsResult

import android.webkit.WebChromeClient

import android.graphics.Bitmap
import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import com.ye.compose.repository.MyLocationListener.*
import com.ye.compose.repository.MyLocationListener.Companion.lat
import com.ye.compose.repository.MyLocationListener.Companion.lng
import com.ye.compose.repository.MyLocationListener.Companion.loadingSuccess
import kotlinx.coroutines.delay


@ExperimentalMaterialApi
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Feed(navController: NavController,appViewModel: AppViewModel){
    val scaffoldState = rememberBottomSheetScaffoldState()
    var dayNum by remember { mutableStateOf(1)}
    var city by remember { mutableStateOf("大连")}
    BottomSheetScaffold(
        sheetContent = {
            Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Start) {
                    dayNum=spendDayMenu(mItems = spend_days)
                    city=cityMenu(mItems = cityList)
            }
        },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    appViewModel.planning(city = city,dayNum = dayNum,mustSlight = arrayListOf(),noSlight = arrayListOf())
                }
            ) {
                androidx.compose.material3.Icon(Icons.Default.Send, contentDescription = "Localized description")
            }
        },
        sheetPeekHeight = 50.dp,

    ) {
        var mapShow by remember { mutableStateOf(false)}
        LaunchedEffect(true){
            delay(500)
            mapShow= loadingSuccess
        }
            AnimatedVisibility(
                visible = mapShow
            ) {
                AndroidViewBindingPage(lat = lat,lng = lng)
            }
        }

}
val spend_days= listOf<Int>(1,2,3,4,5,6,7,8,9,10)
val cityList= listOf<String>("大连")
@Composable
fun spendDayMenu(mItems:List<Int>):Int{
    var expanded by remember { mutableStateOf(false) }
    var dayNum by remember { mutableStateOf(1)}
    Box() {
        androidx.compose.material3.TextButton(onClick = { expanded = true }) {
            androidx.compose.material3.Icon(Icons.Default.DateRange, contentDescription = "Localized description")
            androidx.compose.material3.Text(text = dayNum.toString()+"天")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            mItems.forEach { items->
                DropdownMenuItem(onClick = { dayNum=items;expanded=false}) {
                    Text(text = items.toString()+"天")
                }
            }
        }
    }
    return dayNum
}
@Composable
fun cityMenu(mItems: List<String>):String{
    var expanded by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("大连")}
    Box() {
        androidx.compose.material3.TextButton(onClick = { expanded = true }) {
            androidx.compose.material3.Icon(Icons.Default.Send, contentDescription = "Localized description")
            androidx.compose.material3.Text(text = city)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            mItems.forEach { items->
                DropdownMenuItem(onClick = { city=items;expanded=false}) {
                    Text(text = items)
                }
            }
        }
    }
    return city
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AndroidViewBindingPage(lat:Double,lng:Double){
    val lifecycle= LocalLifecycleOwner.current.lifecycle
            AndroidViewBinding(WebViewBinding::inflate){
                webView.settings.javaScriptEnabled = true
                webView.loadUrl("http://192.168.31.104/getMap/lat=${lat}&lng=${lng}")
                val observer= LifecycleEventObserver{ _,event->
                    when(event){
                        Lifecycle.Event.ON_RESUME->webView.onResume()
                        Lifecycle.Event.ON_PAUSE->webView.onPause()
                        Lifecycle.Event.ON_DESTROY->webView.destroy()
                    }
                }
                lifecycle.addObserver(observer)

        }

        }
