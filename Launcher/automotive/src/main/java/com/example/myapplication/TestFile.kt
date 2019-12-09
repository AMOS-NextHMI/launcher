package com.example.myapplication
import android.car.Car
import android.car.hardware.CarSensorEvent
import android.car.hardware.CarSensorManager
import android.content.ComponentName
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestFile : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d("created", "this work")

    }

    override fun onStart() {
        super.onStart()
        println("schwupps")
    }

    override fun onResume() {
        super.onResume()
        println("yoooo")

    }
}
