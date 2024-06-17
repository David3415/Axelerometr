package com.example.axelerometr.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.axelerometr.EditActivity
import com.example.axelerometr.R
import com.example.axelerometr.constance.Constance

class DbAdapter(listMain: ArrayList<ListItem>, contextM: Context) :
    RecyclerView.Adapter<DbAdapter.MyHolder>() {
    var listArray = listMain   // listMain - Список из активити
    var context = contextM

    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tvGradus: TextView = itemView.findViewById(R.id.tvGradus)////это rc_item
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)////это rc_item
        val context = contextV
        fun setData(item: ListItem) {
            tvGradus.text = item.gradus
             tvComment.text = item.title
            itemView.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(Constance.I_GRADUS_KEY, item.gradus)
                    putExtra(Constance.I_COMMENT_KEY, item.title)
                    putExtra(Constance.I_URI_KEY, item.uri)
                    putExtra(Constance.I_ID_KEY, item.id)
                }
                context.startActivity(intent)
            }
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

    override fun onBindViewHolder(//получаем доступ к textView и позицию в RecyclerView и начинаем заполнять
        holder: MyHolder,
        position: Int
    ) {
        holder.setData(listArray.get(position))
    }

    ////-------------------------------------------------------------------------------
    fun updateAdapter(listItems: List<ListItem>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, dbManager: DbManager) {
        dbManager.removeFromDb(listArray[position].id.toString())
        listArray.removeAt(position)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(position)
    }

}