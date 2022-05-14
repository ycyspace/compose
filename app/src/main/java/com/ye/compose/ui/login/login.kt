


import android.annotation.SuppressLint

import android.util.  Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ye.compose.home
import com.ye.compose.model.User
import com.ye.compose.repository.Status
import com.ye.compose.ui.AppViewModel
import com.ye.compose.utils.toastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@ExperimentalMaterial3Api
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun login(mainNavController: NavController,appViewModel: AppViewModel) {
    val scaffoldState = rememberScaffoldState()
    var phone_number by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val netStatus = appViewModel.getState().observeAsState()
    val status by remember { mutableStateOf(netStatus) }
    val progressStatus = remember { mutableStateOf(0F) }
    var isLoading by remember { mutableStateOf(false) }
    Scaffold(scaffoldState = scaffoldState) {}
    if (status.value != null && isLoading) {
        progressStatus.value = 0F
        LaunchedEffect(status.value) {
            if (status.value!!.status == 0) {
                mainNavController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            } else {
                scaffoldState.snackbarHostState.showSnackbar(status.value!!.message)
                appViewModel.clearStatus()
            }
            isLoading = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val width = 300.dp
        val height = 60.dp
        LinearProgressIndicator(
            modifier = Modifier.alpha(progressStatus.value)
        )
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = phone_number,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "user"
                )
            },
            onValueChange = { newText -> phone_number = newText },
            label = { Text("手机号") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "user"
                )
            },
            onValueChange = { newText -> password = newText },
            label = { Text(text = "密码") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            androidx.compose.material3.OutlinedButton(
                onClick = {
                    progressStatus.value = 255F
                    isLoading = true
                    appViewModel.login(phone_number, password)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(width / 3)
                    .height(height - 15.dp),
            ) {
                Text(text = "登录")
            }
            Spacer(modifier = Modifier.width(30.dp))
            androidx.compose.material3.OutlinedButton(
                onClick = {
                    progressStatus.value = 255F
                    isLoading = true
                    appViewModel.addUser(phone_number, password)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(width / 3)
                    .height(height - 15.dp),
            ) {
                Text("注册")
            }
        }

    }
}







