package io.ginius.cp.kt.lostfound.kotlin

import com.google.gson.annotations.SerializedName


data class SearchDocumentRequest(
        @SerializedName("command") var command: String, // view_document_by_unique_ref
        @SerializedName("data") var data: Data
) {

    data class Data(
            @SerializedName("doc_unique_id") var docUniqueId: String, // 332211
            @SerializedName("userid") var userid: Int // 8
    )
}