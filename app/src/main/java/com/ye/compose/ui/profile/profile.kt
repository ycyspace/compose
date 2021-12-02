

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ye.compose.R
import com.ye.compose.ui.AppViewModel

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun Profile(mainNavController: NavController,appViewModel: AppViewModel) {
    val userState = appViewModel.getUser().observeAsState()
    val user by remember { mutableStateOf(userState) }
    var userIsNull= user.value?.phone_number ==""
    user.value?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            androidx.compose.material3.Surface(
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(10.dp),
                tonalElevation = 16.dp,
                shadowElevation = 16.dp,

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = rememberImagePainter(data = it.head_img,
                            builder = {
                                transformations(CircleCropTransformation())
                                    .placeholder(R.drawable.head)
                                    .error(R.drawable.head)
                            }),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Column() {
                        androidx.compose.material3.Text(text = it.name,style = MaterialTheme.typography.bodyMedium)
                        androidx.compose.material3.Text(text = it.province + "  " + it.city,style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.width(40.dp),)
                    if (userIsNull) {
                        androidx.compose.material3.TextButton(
                            onClick = {
                                mainNavController.navigate("login") {
                                    popUpTo("profile") {
                                        inclusive = true
                                    }
                                }
                            },
                            // Uses ButtonDefaults.ContentPadding by default
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                top = 10.dp,
                                end = 12.dp,
                                bottom = 10.dp
                            ),
                        ) {
                            // Inner content including an icon and a text label
                            androidx.compose.material3.Icon(
                                Icons.Filled.Person,
                                contentDescription = "Login",
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            androidx.compose.material3.Text(text="登录/注册",style = MaterialTheme.typography.titleMedium)
                        }
                    } else {
                        androidx.compose.material3.TextButton(
                            onClick = {

                            },
                            // Uses ButtonDefaults.ContentPadding by default
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                top = 10.dp,
                                end = 12.dp,
                                bottom = 10.dp
                            ),
                        ) {
                            // Inner content including an icon and a text label
                            androidx.compose.material3.Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            androidx.compose.material3.Text("修改")
                        }

                    }
                }

            }
        }
    }
}
//    val scope = rememberCoroutineScope()
//    val selection = remember { mutableStateOf(1) }
//    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
//    LaunchedEffect(scaffoldState) {
//        scaffoldState.reveal()
//    }
//    BackdropScaffold(
//        scaffoldState = scaffoldState,
//        appBar = {
//            TopAppBar(
//                title = { Text("Backdrop scaffold") },
//                navigationIcon = {
//                    if (scaffoldState.isConcealed) {
//                        IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Localized description")
//                        }
//                    } else {
//                        IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
//                            Icon(Icons.Default.Close, contentDescription = "Localized description")
//                        }
//                    }
//                },
//                actions = {
//                    var clickCount by remember { mutableStateOf(0) }
//                    IconButton(
//                        onClick = {
//                            // show snackbar as a suspend function
//                            scope.launch {
//                                scaffoldState.snackbarHostState
//                                    .showSnackbar("Snackbar #${++clickCount}")
//                            }
//                        }
//                    ) {
//                        Icon(Icons.Default.Favorite, contentDescription = "Localized description")
//                    }
//                },
//                elevation = 0.dp,
//                backgroundColor = Color.Transparent
//            )
//        },
//        backLayerContent = {
//            LazyColumn {
//                items(if (selection.value >= 3) 3 else 5) {
//                    ListItem(
//                        Modifier.clickable {
//                            selection.value = it
//                            scope.launch { scaffoldState.conceal() }
//                        },
//                        text = { Text("Select $it") }
//                    )
//                }
//            }
//        },
//        frontLayerContent = {
//            Text("Selection: ${selection.value}")
//            LazyColumn {
//                items(50) {
//                    ListItem(
//                        text = { Text("Item $it") },
//                        icon = {
//                            Icon(
//                                Icons.Default.Favorite,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    )
//}