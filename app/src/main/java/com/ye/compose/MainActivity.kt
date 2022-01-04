package com.ye.compose

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.ye.compose.ui.AppViewModel
import com.ye.compose.ui.theme.AppTheme
import android.provider.Settings
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.baidu.location.LocationClient




class MainActivity : ComponentActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private val appViewModel: AppViewModel = AppViewModel()
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalPermissionsApi
    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.S)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface() {
                    Sample()
                }

            }
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalMaterial3Api
    @ExperimentalMaterialApi
    @ExperimentalPermissionsApi
    @Composable
    private fun Sample() {
        // Track if the user doesn't want to see the rationale any more.
        val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        var doNotShowRationale by rememberSaveable { mutableStateOf(false) }
        PermissionRequired(
            permissionState = locationPermissionState,
            permissionNotGrantedContent = {
                if (doNotShowRationale) {
                    start(appViewModel = appViewModel)
                } else {
                    Rationale(
                        onDoNotShowRationale = { doNotShowRationale = true },
                        onRequestPermission = { locationPermissionState.launchPermissionRequest() }
                    )
                }
            },
            permissionNotAvailableContent = {
                    start(appViewModel = appViewModel)
            }
        ) {
            start(appViewModel = appViewModel)
        }
    }

    @Composable
    private fun Rationale(
        onDoNotShowRationale: () -> Unit,
        onRequestPermission: () -> Unit
    ) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "权限申请")
                },
                text = {
                    Text(text = "定位权限十分重要！！！！！")
                },
                confirmButton = {
                    TextButton(
                        onClick = onRequestPermission
                    ) {
                        Text("去授权")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick =  onDoNotShowRationale
                    ) {
                        Text("残忍拒绝")
                    }
                }
            )

    }

}

