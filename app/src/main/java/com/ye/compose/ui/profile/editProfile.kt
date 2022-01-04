package com.ye.compose.ui.profile

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ye.compose.R
import com.ye.compose.ui.AppViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun editProfile(mainNavController: NavController, appViewModel: AppViewModel){
    val userState = appViewModel.getUser().observeAsState()
    val user by remember { mutableStateOf(userState) }
    androidx.compose.material3.Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(text ="个人资料" ,style = MaterialTheme.typography.titleMedium) },navigationIcon = { IconButton(
                    onClick = {mainNavController.popBackStack()}
            ){
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            })
        },
        content = {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                     verticalArrangement = Arrangement.spacedBy(10.dp),) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "头像")
                                Image(
                                    painter = rememberImagePainter(data = user.value!!.head_img,
                                        builder = {
                                            transformations(CircleCropTransformation())
                                                .placeholder(R.drawable.head)
                                                .error(R.drawable.head)
                                        }),
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )
                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val canvasWidth = size.width
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = 0f),
                                    color = Color.LightGray
                                )
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                                Text(text = "UID")
                                Text(text = user.value!!.uid.toString(),style = MaterialTheme.typography.bodySmall)

                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val canvasWidth = size.width
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = 0f),
                                    color = Color.LightGray
                                )
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                                Text(text = "名称")
                                Text(text = user.value!!.name,style = MaterialTheme.typography.bodySmall)
                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val canvasWidth = size.width
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = 0f),
                                    color = Color.LightGray
                                )
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                                Text(text = "性别")
                                Text(text = "五头像",style = MaterialTheme.typography.bodySmall)

                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val canvasWidth = size.width
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = 0f),
                                    color = Color.LightGray
                                )
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                                Text(text = "出生年月")
                                Text(text = "五头像",style = MaterialTheme.typography.bodySmall)

                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val canvasWidth = size.width
                                drawLine(
                                    start = Offset(x = canvasWidth, y = 0f),
                                    end = Offset(x = 0f, y = 0f),
                                    color = Color.LightGray
                                )
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                                Text(text = "省份/城市")
                                Text(text = "辽宁 大连",style = MaterialTheme.typography.bodySmall)

                            }
                    Canvas(modifier = Modifier.fillMaxWidth()) {
                        val canvasWidth = size.width
                        drawLine(
                            start = Offset(x = canvasWidth, y = 0f),
                            end = Offset(x = 0f, y = 0f),
                            color = Color.LightGray
                        )
                    }
                    TextButton(
                        onClick = {
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Filled.ExitToApp,
                            contentDescription = "Exit",
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text="退出登录",style = MaterialTheme.typography.titleMedium)
                    }

                }

        }
    )
}