package com.bind.databindingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bind.databindingtest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding()

        val a = object: Any(){
            fun a() = "test"
        }

        a.a()
    }

    fun databinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.main = this
    }
    fun ocListener(){
        Toast.makeText(this, "button is Pressed", Toast.LENGTH_SHORT).show()
    }
}