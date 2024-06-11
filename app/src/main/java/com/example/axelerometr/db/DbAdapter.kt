package com.example.axelerometr.db

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.Recycler

class DbAdapter:RecyclerView.Adapter<DbAdapter.myHolder>() {
    class myHolder(itemView:View): RecyclerView.ViewHolder(itemView){

    }
////Запускается каждый раз при рисовании одного rc_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {// Создаём myHolder
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {//получаем доступ к textView и позицию в RecyclerView и начинаем заполнять
        TODO("Not yet implemented")
    }
    ////-------------------------------------------------------------------------------
}