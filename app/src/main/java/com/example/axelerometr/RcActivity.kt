package com.example.axelerometr

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.axelerometr.databinding.ActivityRcBinding
import com.example.axelerometr.db.DbAdapter
import com.example.axelerometr.db.DbManager

class RcActivity : AppCompatActivity() {
    lateinit var bindingRc: ActivityRcBinding
    val dbManager = DbManager(this)
    val dbAdapter = DbAdapter(ArrayList(), this)

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
        bindingRc.rcView2.adapter = dbAdapter
        val swapHelper=getSwapMg()
        swapHelper.attachToRecyclerView(bindingRc.rcView2)
    }

    fun fillRcAdapter() {
        val list = dbManager.readDbData()
        dbAdapter.updateAdapter(list)
    }

    private fun getSwapMg(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dbAdapter.removeItem(viewHolder.adapterPosition, dbManager)
            }
        })
    }
}


