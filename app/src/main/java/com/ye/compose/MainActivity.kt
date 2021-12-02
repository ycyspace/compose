package com.ye.compose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.WindowCompat
import com.ye.compose.ui.AppViewModel
import com.ye.compose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val appViewModel:AppViewModel= AppViewModel()
    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.S)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface() {
                    start(appViewModel = appViewModel)
                }

            }
        }
    }
}
