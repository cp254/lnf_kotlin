package io.ginius.cp.kt.lostfound.kotlin

import com.google.gson.annotations.SerializedName


data class SearchHistoryResponse(
        @SerializedName("statuscode") var statuscode: Int, // 0
        @SerializedName("statusname") var statusname: String, // success
        @SerializedName("result") var result: List<Result>
) {

    data class Result(
            @SerializedName("_id") var id: String, // 5b66eb3aa071e214a0265b6b
            @SerializedName("user_id") var userId: String, // 8
            @SerializedName("doc_ref") var docRef: String, // 332211
            @SerializedName("viewdate") var viewdate: String, // 2018-08-05T12:19:06.517Z
            @SerializedName("view_id") var viewId: Int, // 428
            @SerializedName("__v") var v: Int // 0
    )
}