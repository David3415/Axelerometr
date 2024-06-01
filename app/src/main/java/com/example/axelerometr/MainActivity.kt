package com.example.axelerometr

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.axelerometr.constance.Constance
import com.example.axelerometr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sManager: SensorManager

    private var magnetic = FloatArray(9)
    private var gravity = FloatArray(9)

    private var accel = FloatArray(3)
    private var maqf = FloatArray(3)
    private var values = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorAcc = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensorMf = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val sListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> accel = event.values
                    Sensor.TYPE_MAGNETIC_FIELD -> maqf = event.values
                }
                SensorManager.getRotationMatrix(gravity, magnetic, accel, maqf)
                val outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    outGravity
                )

                SensorManager.getOrientation(outGravity, values)
                val degree = values[2] * Constance.RADIAN
                val rotate = 270 + degree*2

                binding.lRotation.rotation = rotate - Constance.R_ANGLE

                val color = if ((rotate - Constance.R_ANGLE * 3).toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                binding.lRotation.setBackgroundColor(color)
                binding.tvSensor.text = (rotate - Constance.R_ANGLE * 3).toString()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


            }

        }
        sManager.registerListener(sListener, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListener, sensorMf, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
                Toast.makeText(this@MainActivity, "Land", Toast.LENGTH_LONG).show()
            else Toast.makeText(this@MainActivity, "Port", Toast.LENGTH_LONG).show()
        }
    }
}

