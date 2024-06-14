package com.example.axelerometr

import android.app.LauncherActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.axelerometr.constance.Constance
import com.example.axelerometr.databinding.ActivityEditBinding
import com.example.axelerometr.db.DbManager

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    val dbManager = DbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        binding.edDesc.setText(i.getStringExtra(Constance.I_VAL_KEY))
        binding.btnStore.setOnClickListener {
            storeVal(it)
        }
    }

    fun storeVal(view: View) {
        val tmp1: TextView = findViewById(R.id.edDesc)
        val tmp: TextView = findViewById(R.id.edTitle)
        val myTitle = tmp.text.toString()

        val i = intent
        if (i != null) {

            if (i.getStringExtra(Constance.I_VAL_KEY) != null) {
                val myDesk = tmp1.text.toString()
                if (myTitle != "" && myDesk != "") {
                    binding.edDesc.setText(i.getStringExtra(Constance.I_VAL_KEY))
                    var temp = i.getStringExtra(Constance.I_VAL_KEY)
                    dbManager.insertToDb(
                        myTitle, temp.toString()
                    )
                }
            }
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }
}