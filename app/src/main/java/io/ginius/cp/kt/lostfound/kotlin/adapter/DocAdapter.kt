package io.ginius.cp.kt.lostfound.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ginius.cp.kt.lostfound.R
import io.ginius.cp.kt.lostfound.kotlin.api.post.SearchDoc
import kotlinx.android.synthetic.main.search_item.view.*

class DocAdapter(private val documentList: List<SearchDoc.Result>, private val listener: (SearchDoc.Result) -> Unit): RecyclerView.Adapter<DocAdapter.DocumentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DocumentHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false))

    override fun onBindViewHolder(holder: DocumentHolder, position: Int) = holder.bind(documentList[position], listener)

    override fun getItemCount() = documentList.size

    class DocumentHolder(documentView: View): RecyclerView.ViewHolder(documentView) {

        fun bind(doc: SearchDoc.Result, listener: (SearchDoc.Result) -> Unit) = with(itemView) {
            doc_type.text = doc.doc_name
            doc_id.text = doc._id

        }
    }
}
