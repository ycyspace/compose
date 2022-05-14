


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.ye.compose.ui.AppViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ye.compose.databinding.WebViewBinding
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.livedata.observeAsState
import com.ye.compose.MyApplication.Companion.BASE_URL
import com.ye.compose.MyApplication.Companion.sightList
import com.ye.compose.model.Card
import com.ye.compose.model.sight
import kotlinx.coroutines.delay
import java.lang.Thread.sleep


@ExperimentalMaterialApi
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Feed(navController: NavController,appViewModel: AppViewModel){
    val scaffoldState = rememberBottomSheetScaffoldState()
    val cityList= listOf("大连")
    val reasonList= listOf<String>("文化","健康","购物","交往","业务")
    val sightName= sightList
    val spendDays= listOf(1,2,3,4,5,6,7)
    var dayNum by remember { mutableStateOf(1)}
    var lat=appViewModel.getLat().observeAsState()
    var lng=appViewModel.getLng().observeAsState()
    var city by remember { mutableStateOf("大连")}
    var reason by remember {mutableStateOf("文化")}
    BottomSheetScaffold(
        sheetContent = {
            Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Start) {
                    dayNum=spendDayMenu(mItems = spendDays)
                    city=cityMenu(mItems = cityList)
                    reason=tripReason(mItems = reasonList)
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
        lat.value?.let { it1 -> lng.value?.let { it2 -> AndroidViewBindingPage(lat = it1, lng = it2) } }
        }

}

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
fun tripReason(mItems: List<String>):String{
    var expanded by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("文化")}
    Box() {
        androidx.compose.material3.TextButton(onClick = { expanded = true }) {
            androidx.compose.material3.Icon(Icons.Default.Favorite, contentDescription = "Localized description")
            androidx.compose.material3.Text(text = reason)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            mItems.forEach { items->
                DropdownMenuItem(onClick = { reason=items;expanded=false}) {
                    Text(text = items)
                }
            }
        }
    }
    return reason
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
                webView.loadUrl(BASE_URL+"/getMap/lat=${lat}&lng=${lng}")
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
