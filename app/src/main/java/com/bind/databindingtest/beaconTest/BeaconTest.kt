package com.bind.databindingtest.beaconTest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.bind.databindingtest.databinding.ActivityMainBinding
import com.minew.beacon.BeaconValueIndex
import com.minew.beacon.BluetoothState
import com.minew.beacon.MinewBeacon
import com.minew.beacon.MinewBeaconManager
import com.minew.beacon.MinewBeaconManagerListener

class BeaconTest {

    fun beconListener(
        mMinewBeaconManager: MinewBeaconManager,
        context: Context,
        binding: ActivityMainBinding
    ){
        mMinewBeaconManager?.setDeviceManagerDelegateListener(object: MinewBeaconManagerListener{
            override fun onAppearBeacons(beacons: MutableList<MinewBeacon>) {
                for(item in beacons.iterator()){
                    val beaconName = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).stringValue
                    if(beaconName.contains("gs")) {
                        val txPower = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_TxPower).intValue
                        Log.d("txPower", txPower.toString())
                        binding.beaconText.text = "${binding.beaconText.text}\n$beaconName"
                    }
                }
            }

            override fun onDisappearBeacons(beacons: MutableList<MinewBeacon>) {
            }

            override fun onRangeBeacons(beacons: MutableList<MinewBeacon>) {
                for(item in beacons.iterator()){
                    val beaconName = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).stringValue
                    if(beaconName.contains("gs")) {
                        val txPower = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_TxPower).intValue
                        Log.d("txPower", txPower.toString())
                        binding.beaconText.text = "${binding.beaconText.text}\n$beaconName"
                    }
                }
            }

            override fun onUpdateState(beacons: BluetoothState) {
            }
        })
    }
}