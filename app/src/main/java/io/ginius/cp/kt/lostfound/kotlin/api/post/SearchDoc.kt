package io.ginius.cp.kt.lostfound.kotlin.api.post

import com.google.gson.annotations.SerializedName

object SearchDoc {

    data class Request(
            @SerializedName("command")val command: String, //200
            @SerializedName("data") val data: Data
    )

    data class Data(
            @SerializedName("doc_ref")val doc_ref: String)

    data class Response(
                        val statuscode: Int, //200
                        val statusname: String,
                        val result: List <Result>)

    data class Result(val _id: String,
                      val doc_type: String,
                      val doc_name: String,
                      val doc_details: String,
                      val doc_num: Int,
                      val score: Int)
}