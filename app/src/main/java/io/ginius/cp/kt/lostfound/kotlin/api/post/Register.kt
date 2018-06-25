package io.ginius.cp.kt.lostfound.kotlin.api.post

import com.google.gson.annotations.SerializedName

object Register {


    data class Request(
            @SerializedName("command")val command: String, //200
            @SerializedName("data") val data: Data
    )

    data class Data(
            @SerializedName("password") val Password: String = "",
            @SerializedName("reg_by") val RegBy: String = "",
            @SerializedName("contact") val  contact: String = "",
            @SerializedName("email") val email: String = "")

    data class Response(
            val statuscode: Int, //200
            val statusname: String,
            val result: Int
    )


}