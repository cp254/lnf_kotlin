package io.ginius.cp.kt.lostfound.kotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ginius.cp.kt.lostfound.R
import kotlinx.android.synthetic.main.history_item.view.*

class DocSearchAdapter(val items: List<SearchHistoryResponse.Result>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.tvDocId.text = "Document Id : " + items.get(position).docRef
        holder.tvDate.text = "Date Searched : " + items.get(position).viewdate.take(10)
        Log.e("First call", items.get(position).docRef)
        Log.e("lana", items.get(position).viewdate.take(10))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.animal_list_item, parent, false))
//    }
//
//    // Binds each animal in the ArrayList to a view
//    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        holder?.tvAnimalType?.text = items.get(position)
//    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvDocId = view.doc_type
    val tvDate = view.doc_id
}