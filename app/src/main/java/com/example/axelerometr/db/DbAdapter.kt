package com.example.axelerometr.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.axelerometr.R

class DbAdapter(listMain: ArrayList<ListItem>, contextM: Context) :
    RecyclerView.Adapter<DbAdapter.MyHolder>() {
    var listArray = listMain//// это  ArrayList<ListItem> со всеми строками RV
    var context = contextM

    ////   MyHolder - это тип шаблона адаптера - <MyAdapter.MyHolder>, т.е. у  MyAdapter тип - MyHolder
    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)////это rc_item
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)////это rc_item
        val context = contextV
        fun setData(item: ListItem) {

            tvTitle.text = item.title
            tvTime.text = item.desc
            /*itemView.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(MyIntentConstances.I_TITLE_KEY,item.title)
                    putExtra(MyIntentConstances.I_DESK_KEY,item.desc)
                                    }
                context.startActivity(intent)
            }*/
        }
    }

    ////Запускается каждый раз при рисовании одного rc_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }

    fun updateAdapter(listItems: List<ListItem>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int,dbManager: DbManager) {
        dbManager.removeFromDb(listArray[position].id.toString())
        listArray.removeAt(position)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(position)
    }
}