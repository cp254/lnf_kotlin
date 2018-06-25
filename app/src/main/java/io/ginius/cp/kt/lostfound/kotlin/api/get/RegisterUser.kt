package io.ginius.cp.kt.lostfound.kotlin.api.get

object RegisterUser {
    data class Result(val data: Data,
                      val command: String)
    data class Data(val email: String,
                    val contact: String,
                    val password: String,
                    val reg_by: String)
}