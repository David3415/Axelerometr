package com.example.axelerometr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.axelerometr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sListener = object : SensorEventListener {
            override fun onSensorChanged(sEvent: SensorEvent?) {
                val value = sEvent?.values
                val xData="X: ${value?.get(0)}"
                val yData="X: ${value?.get(1)}"
                val zData="X: ${value?.get(2)}"
                binding.tvSensorX.text=xData
                binding.tvSensorY.text=yData
                binding.tvSensorZ.text=zData
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
        sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
}