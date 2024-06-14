package com.example.axelerometr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axelerometr.databinding.ActivityRcBinding
import com.example.axelerometr.db.DbAdapter
import com.example.axelerometr.db.DbManager

class RcActivity : AppCompatActivity() {
    lateinit var bindingRc: ActivityRcBinding
    val DbManager = DbManager(this)
    val myAdapter = DbAdapter(ArrayList(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingRc = ActivityRcBinding.inflate(layoutInflater)
        setContentView(bindingRc.root)
        init()
        fillRcAdapter()
    }

    fun init() {
        bindingRc.rcView2.layoutManager = LinearLayoutManager(this)
        bindingRc.rcView2.adapter = myAdapter
    }

    fun fillRcAdapter() {
        val list = DbManager.readDbData()
        myAdapter.updateAdapter(list)
    }
}


