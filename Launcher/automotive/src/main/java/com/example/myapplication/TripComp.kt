package com.example.myapplication

import android.car.Car
import android.car.hardware.CarSensorManager
import android.content.ComponentName
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.car.carlauncher.R

class TripComp : AppCompatActivity() {



    private lateinit var car : Car
    private val permissions = arrayOf(Car.PERMISSION_SPEED)
    public var tripCompFragment: TripCompFragment = TripCompFragment();

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("helllllloo yo","onCreate");
        super.onCreate(savedInstanceState)

        initCar()
        setContentView(R.layout.comp_layout)
        val extra = intent.getStringExtra("key")
        tripCompFragment.sourceValues(extra,"lol");
//        val intent = Intent(this, TripComp::class.java)
////        intent.putExtra("key", value)
//        startActivity(intent)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.playback, tripCompFragment)
        fragmentTransaction.commitNow()



    }

    fun getFragment(): Fragment{
        Log.d("helllllloo yo","getFragment");
        return this.tripCompFragment;
    }

    override fun onStart() {
        Log.d("helllllloo yo","onStart");
        super.onStart();

    }


    override fun onResume() {
        super.onResume()

        if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            if (!car.isConnected && !car.isConnecting) {
                car.connect()
            }
        } else {
            requestPermissions(permissions, 0)
        }

    }

    override fun onPause() {
        if(car.isConnected) {
            car.disconnect()
        }

        super.onPause()
    }

    private fun initCar() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            return
        }

        if(::car.isInitialized) {
            return
        }

        car = Car.createCar(this, object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                onCarServiceReady()
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                // on failure callback
            }
        })
    }

    private fun onCarServiceReady() {
        watchSpeedSensor()
    }

    private fun watchSpeedSensor() {

        val sensorManager = car.getCarManager(Car.SENSOR_SERVICE) as CarSensorManager

        sensorManager.registerListener(
                { carSensorEvent ->
                    this.tripCompFragment.sourceValues(carSensorEvent.floatValues[0].toString(),"bla");
                    Log.d("MainActivity", "Speed: ${carSensorEvent.floatValues[0]}")
                    var speedTextView = findViewById<TextView>(R.id.speedTextView)
                    speedTextView.text = "Speed: " + carSensorEvent.floatValues[0].toString() + "km/h"

                    var gear = findViewById<TextView>(R.id.gearTextView)
                    print("this is my event ")
                    print(carSensorEvent)

                },
                CarSensorManager.SENSOR_TYPE_CAR_SPEED,
                CarSensorManager.SENSOR_RATE_NORMAL

        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions[0] == Car.PERMISSION_SPEED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!car.isConnected && !car.isConnecting) {
                car.connect()
            }
        }
    }
}
