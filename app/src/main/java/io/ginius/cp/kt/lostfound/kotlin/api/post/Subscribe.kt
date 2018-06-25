package io.ginius.cp.kt.lostfound.kotlin.api.post

object Subscribe {

    data class Request(val data: Data,
                      val command: String)


    data class Data(val doc_ref: String,
                    val doc_type: String,
                    val user_ref: String,
                    val notification_type: String)

    data class Response(
            val statuscode: Int, //200
            val statusname: String,
            val result: Int
    )


}