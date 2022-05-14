package com.ye.compose.repository;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

public class LocationListener extends BDAbstractLocationListener {
    GetGps getGps;
    public interface GetGps{
        void getGps(BDLocation bdLocation);
    }
    public void setListenGps(GetGps getGps){
        this.getGps=getGps;
    }
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        getGps.getGps(bdLocation);
    }
}
