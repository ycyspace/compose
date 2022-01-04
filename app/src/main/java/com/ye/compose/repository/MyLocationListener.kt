package com.ye.compose.repository

import android.util.Log
import androidx.compose.ui.window.Dialog
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess
import kotlin.properties.Delegates

class MyLocationListener: BDAbstractLocationListener() {
    companion object{
        var lat by Delegates.notNull<Double>()
        var lng by Delegates.notNull<Double>()
        var loadingSuccess =false
    }
    override fun onReceiveLocation(p0: BDLocation?) {
        if (p0 != null) {
            if(p0.latitude!=4.9E-324){
                lat= p0.latitude
            }
        }
        if (p0 != null) {
            if(p0.longitude!=4.9E-324){
                lng= p0.longitude
            }
        }
        loadingSuccess=true

    }
}