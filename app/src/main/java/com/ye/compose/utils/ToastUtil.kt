package com.ye.compose.utils


import android.util.Log
import android.widget.Toast
import com.ye.compose.MyApplication.Companion.context

fun String.toastUtil(){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}