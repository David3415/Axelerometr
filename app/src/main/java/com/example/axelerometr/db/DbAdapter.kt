package com.example.axelerometr.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.axelerometr.R

class DbAdapter(listMain: ArrayList<String>) : RecyclerView.Adapter<DbAdapter.MyHolder>() {
    var listArray = listMain   // listMain - Список из активити

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun setData(title: String) {
            tvTitle.text = title
        }
    }


    ////Запускается каждый раз при рисовании одного rc_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(
            inflater.inflate(R.layout.rc_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(//получаем доступ к textView и позицию в RecyclerView и начинаем заполнять
        holder: MyHolder,
        position: Int
    ) {
        holder.setData(listArray.get(position))
    }

    ////-------------------------------------------------------------------------------
    fun updateAdapter(listItems: List<String>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }
}