package com.bind.databindingtest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bind.databindingtest.beaconTest.BeaconTest
import com.bind.databindingtest.bleTest.BleConnectManager
import com.bind.databindingtest.databinding.ActivityMainBinding
import com.bind.databindingtest.foreground.ForegroundService
import com.clj.fastble.BleManager
import com.minew.beacon.MinewBeaconManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bleConnectManager = BleConnectManager()
    private val foregroundService = ForegroundService()
    private lateinit var mMinewBeaconManager: MinewBeaconManager
    private val beaconTest = BeaconTest()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding()

        checkPermission()
        bleConnectManager.initBeacone(application)
        mMinewBeaconManager = MinewBeaconManager.getInstance(this)
        beaconTest.beconListener(mMinewBeaconManager, this, binding)
    }

    fun checkPermission() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.POST_NOTIFICATIONS
            ), 0
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d("Current lifecycle callback", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Current lifecycle callback", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Current lifecycle callback", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Current lifecycle callback", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Current lifecycle callback", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Current lifecycle callback", "onDestroy")
    }

    fun databinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.main = this
    }

    fun ocListener() {
        Toast.makeText(this, "button is Pressed", Toast.LENGTH_SHORT).show()
        //원스토어 다이렉트 링크
//        val ci: Intent = Uri.parse("https://m.onestore.co.kr/mobilepoc/apps/appsDetail.omp?prodId=0000764907").let { webpage ->
//            Intent(Intent.ACTION_VIEW, webpage)
//        }
////        startActivity(ci)
//        jsoupTest()
        bleConnectManager.scanBeacon(binding)
    }

    fun beaconStopListener(){
        bleConnectManager.cancelScan()
    }

    fun startForeground() {
        val intent = Intent(applicationContext, ForegroundService::class.java)
        startForegroundService(intent)
    }

    fun stopForeground() = foregroundService.stopForeground()

    fun startHysorBeaconScan(){
        mMinewBeaconManager.startScan()
    }

    fun stopHysorBeaconScan(){
        mMinewBeaconManager.stopScan()
    }

    fun jsoupTest() {
        val oneStoreUrl =
            "https://m.onestore.co.kr/mobilepoc/web/apps/appsDetail/spec.omp?prodId=0000764907"
        val tagClassName = ".detaildescriptionclamp-date"

        CoroutineScope(Dispatchers.Default).launch {
            val doc = Jsoup.connect(oneStoreUrl).get()
            val element = doc.select(tagClassName)
            Log.d("sdl", element.text())
        }
    }

}