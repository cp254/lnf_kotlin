package io.ginius.cp.kt.lostfound.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ginius.cp.kt.lostfound.R
import io.ginius.cp.kt.lostfound.kotlin.SearchDocumentResponse
import kotlinx.android.synthetic.main.search_item.view.*

class DocAdapter(val items: SearchDocumentResponse.Result.Doc, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.tvDocType?.text = items.docDetails
        holder.tvDocId?.text = items.docUniqueId
        holder.tvDocName?.text = items.docFname + " " + items.docLname

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return 1
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
    val tvDocType = view.doc_type
    val tvDocId = view.doc_id
    val tvDocName = view.doc_name
}
