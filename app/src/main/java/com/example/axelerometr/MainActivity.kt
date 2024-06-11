package com.example.axelerometr

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.axelerometr.constance.Constance
import com.example.axelerometr.databinding.ActivityMainBinding
import com.example.axelerometr.db.DbHelper

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sManager: SensorManager

    private var magnetic = FloatArray(9)
    private var gravity = FloatArray(9)

    private var accel = FloatArray(3)
    private var maqf = FloatArray(3)
    private var values = FloatArray(3)
    var rotate: Float = 0.0f
    var degree: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setIcon(R.drawable.ic_settings)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorAcc = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensorMf = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val dbHelper=DbHelper(this)
        var db: SQLiteDatabase? = null
        dbHelper.writableDatabase
        dbHelper.onCreate(db)

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
                degree = values[2] * Constance.RADIAN
                rotate = 270 + degree

                binding.lRotation.rotation = rotate - Constance.R_ANGLE

                val color = if ((rotate - Constance.R_ANGLE * 3).toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                binding.lRotation.setBackgroundColor(color)
                binding.tvSensor.text = (rotate - Constance.R_ANGLE * 3).toString()
                binding.apply {
//main.id.l_
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

        }
        sManager.registerListener(sListener, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListener, sensorMf, SensorManager.SENSOR_DELAY_NORMAL)

        binding.btnStoreVal?.setOnClickListener {
            storeValue()
        }
        /*binding.btnApply?.setOnKeyListener{
           textSizeFun(R.id.tvSlideTitle)

        }*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_orientation) {
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                Configuration.ORIENTATION_LANDSCAPE -> setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                else -> ""
            }
        }
        return true
    }

    private fun storeValue() {

    }


    fun textSizeFun(view: View) {
        var textSize = R.id.tvSlideTitle
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                degree = values[2] * Constance.RADIAN
                rotate = 180 + degree

                binding.lRotation.rotation = rotate - Constance.R_ANGLE - 90

                val color = if ((rotate - Constance.R_ANGLE * 3).toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                binding.lRotation.setBackgroundColor(color)
                binding.tvSensor.text = (rotate - Constance.R_ANGLE * 3).toString()
                Toast.makeText(this@MainActivity, "Land", Toast.LENGTH_LONG).show()
            } else Toast.makeText(this@MainActivity, "Port", Toast.LENGTH_LONG).show()
        }
    }
}

