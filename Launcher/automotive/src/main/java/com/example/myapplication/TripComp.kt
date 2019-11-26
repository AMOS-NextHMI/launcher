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
import com.android.car.carlauncher.R

class TripComp : AppCompatActivity() {

    private lateinit var car : Car
    private val permissions = arrayOf(Car.PERMISSION_SPEED, Car.PERMISSION_POWERTRAIN)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_launcher)

        initCar()
    }

    override fun onResume() {
        super.onResume()

        var allPermissionsGranted = true
        for (perm in permissions) {
            if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false
                break
            }
        }
        if(allPermissionsGranted) {
            if (!car.isConnected && !car.isConnecting) {
                car.connect()
            }
        } else {
            Log.i("permission", "requesting permission")
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
        val sensorManager = car.getCarManager(Car.SENSOR_SERVICE) as CarSensorManager
        watchSpeedSensor(sensorManager)
        watchGearSensor(sensorManager)
        //watchFuelLevel(sensorManager)

        //watchSpeedMilage(sensorManager)
        //watchOilLevel(sensorManager)

    }

    private fun watchFuelLevel(sensorManager: CarSensorManager) {
        sensorManager.registerListener(
                { carSensorEvent ->
                    Log.i("fuel", carSensorEvent.floatValues[0].toString())
                    Log.i("fuel", carSensorEvent.intValues[0].toString())









                },
                CarSensorManager.SENSOR_TYPE_FUEL_LEVEL,
                CarSensorManager.SENSOR_RATE_NORMAL

        )

    }


    private fun watchGearSensor(sensorManager: CarSensorManager) {
        sensorManager.registerListener(
                { carSensorEvent ->



                    var gearTextView = findViewById<TextView>(R.id.gearTextView)


                    when (carSensorEvent.intValues[0]) {
                        CarSensorEvent.GEAR_DRIVE -> gearTextView.text = "Gear: D"
                        CarSensorEvent.GEAR_NEUTRAL -> gearTextView.text = "Gear: N"
                        CarSensorEvent.GEAR_REVERSE -> gearTextView.text = "Gear: R"
                        CarSensorEvent.GEAR_PARK -> gearTextView.text = "Gear: P"
                        else -> { // Note the block
                            Log.i("gear" ,"Gear not implemented")
                        }
                    }





                },
                CarSensorManager.SENSOR_TYPE_GEAR,
                CarSensorManager.SENSOR_RATE_NORMAL

        )


    }

    private fun watchSpeedMilage(sensorManager: CarSensorManager) {
        // TODO
    }


    private fun watchSpeedSensor(sensorManager: CarSensorManager) {
        sensorManager.registerListener(
                { carSensorEvent ->

                    var speedTextView = findViewById<TextView>(R.id.speedTextView)

                        speedTextView.text = "Speed: " + carSensorEvent.floatValues[0].toString() + "km/h"



                },
                CarSensorManager.SENSOR_TYPE_CAR_SPEED,
                CarSensorManager.SENSOR_RATE_NORMAL

        )
    }

    private fun watchOilLevel(sensorManager: CarSensorManager) {

        sensorManager.registerListener(
                { carSensorEvent ->
                    println(carSensorEvent.toString())
                    //var engineOilTextView = findViewById<TextView>(R.id.engineOilTextView)
                    //engineOilTextView.text = "Engine Oil Level: " + carSensorEvent.toString();



                },
                CarSensorManager.SENSOR_TYPE_ENGINE_OIL_LEVEL,
                CarSensorManager.SENSOR_RATE_NORMAL




        )
    }

    private fun checkIfPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false

            }
        }
        return true

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allPermissionsGranted = checkIfPermissionsGranted(grantResults)



        if (allPermissionsGranted && !car.isConnected && !car.isConnecting) {
            car.connect()
        }


    }
}
//package com.example.myapplication
//
//import android.car.Car
//import android.car.hardware.CarSensorManager
//import android.content.ComponentName
//import android.content.ServiceConnection
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.os.IBinder
//import android.util.Log
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.android.car.carlauncher.R
//
//class TripComp : AppCompatActivity() {
//
//
//
//    private lateinit var car : Car
//    private val permissions = arrayOf(Car.PERMISSION_SPEED)
//    public var tripCompFragment: TripCompFragment = TripCompFragment();
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("helllllloo yo","onCreate");
//        super.onCreate(savedInstanceState)
//
//        initCar()
//        setContentView(R.layout.comp_layout)
//
//
//    }
//
//    fun getFragment(): Fragment{
//
//        return this.tripCompFragment;
//    }
//
//    override fun onStart() {
//
//        super.onStart();
//
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//
//        if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED) {
//            if (!car.isConnected && !car.isConnecting) {
//                car.connect()
//            }
//        } else {
//            requestPermissions(permissions, 0)
//        }
//
//    }
//
//    override fun onPause() {
//        if(car.isConnected) {
//            car.disconnect()
//        }
//
//        super.onPause()
//    }
//
//    private fun initCar() {
//        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
//            return
//        }
//
//        if(::car.isInitialized) {
//            return
//        }
//
//        car = Car.createCar(this, object : ServiceConnection {
//            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
//                onCarServiceReady()
//            }
//
//            override fun onServiceDisconnected(componentName: ComponentName) {
//                // on failure callback
//            }
//        })
//    }
//
//    private fun onCarServiceReady() {
//        watchSpeedSensor()
//    }
//
//    private fun watchSpeedSensor() {
//
//        val sensorManager = car.getCarManager(Car.SENSOR_SERVICE) as CarSensorManager
//
//        sensorManager.registerListener(
//                { carSensorEvent ->
//                    this.tripCompFragment.sourceValues(carSensorEvent.floatValues[0].toString(),"bla");
//                    Log.d("MainActivity", "Speed: ${carSensorEvent.floatValues[0]}")
//                    var speedTextView = findViewById<TextView>(R.id.speedTextView)
//                    speedTextView.text = "Speed: " + carSensorEvent.floatValues[0].toString() + "km/h"
//
//                    var gear = findViewById<TextView>(R.id.gearTextView)
//                    print("this is my event ")
//                    print(carSensorEvent)
//
//                },
//                CarSensorManager.SENSOR_TYPE_CAR_SPEED,
//                CarSensorManager.SENSOR_RATE_NORMAL
//
//        )
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (permissions[0] == Car.PERMISSION_SPEED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (!car.isConnected && !car.isConnecting) {
//                car.connect()
//            }
//        }
//    }
//}
