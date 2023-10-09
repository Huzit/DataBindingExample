package com.bind.databindingtest.bleTest

import android.app.Application
import android.content.Context
import android.util.Log
import com.bind.databindingtest.databinding.ActivityMainBinding
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import java.util.Timer
import java.util.TimerTask

class BleConnectManager {
    private val TAG = javaClass.simpleName
    fun initBeacone(applicationContext: Application){
        BleManager.getInstance()
            .enableLog(false)
            .setReConnectCount(1, 3000)
            .setSplitWriteNum(20)
            .setConnectOverTime(10000)
            .setOperateTimeout(3000)
            .init(applicationContext)
    }
    lateinit var time: Timer
    fun scanBeacon(binding: ActivityMainBinding){
        time = Timer()
        time.scheduleAtFixedRate(object: TimerTask(){
            override fun run() {
                BleManager.getInstance().scan(object: BleScanCallback(){
                    override fun onScanStarted(success: Boolean) {
                        Log.i(TAG, "비콘 스캔 시작")
                    }

                    override fun onScanning(bleDevice: BleDevice?) {
                        if (bleDevice != null){
                            var name = bleDevice.name ?: ""
                            if (name.contains("ECA")) {
                                Log.d("스캔된 기기", bleDevice.name ?: "")
                                binding.beaconText.text = "${binding.beaconText.text}\n ${bleDevice.name}"
                            }
                        }
                    }

                    override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                        Log.i(TAG, "비콘 스캔 완료")
                    }
                })
            }
            override fun cancel(): Boolean {
                return super.cancel()
            }
        }, 0, 10000)
    }

    fun cancelScan() {
        BleManager.getInstance().cancelScan()
        time.cancel()
    }
}