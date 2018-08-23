package io.ginius.cp.kt.lostfound.kotlin

import com.google.gson.annotations.SerializedName


data class SearchHistoryRequest(
        @SerializedName("command") var command: String, // get_user_search_history
        @SerializedName("data") var data: Data
) {

    data class Data(
            @SerializedName("user") var user: String // 8
    )
}