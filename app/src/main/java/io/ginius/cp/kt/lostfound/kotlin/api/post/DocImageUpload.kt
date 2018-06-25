package io.ginius.cp.kt.lostfound.kotlin.api.post

import com.google.gson.annotations.SerializedName

object DocImageUpload {

//    {
//
//        "command":"upload_doc_image" ,
//        "data":{
//
//        "image_name":"back_image",
//        "base64" :"base64ofimage" ,
//        "doc_ref":"3" ,
//        "uploadby":"3"
//    }
//
//    }

    data class Request(
            @SerializedName("command")val command: String, //200
            @SerializedName("data") val data: Data
    )

    data class Data(
            @SerializedName("image_name") val ImageName: String = "",
            @SerializedName("base64") val Base64: String = "",
            @SerializedName("doc_ref") val  DocRef: String = "",
            @SerializedName("uploadby") val UploadBy: String = "")

    data class Response(
            val statuscode: Int, //200
            val statusname: String,
            val result: Int
    )
}