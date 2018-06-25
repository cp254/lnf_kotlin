package io.ginius.cp.kt.lostfound.kotlin.api.get

object Result {

    data class Result(val statuscode: String,
                      val statusname: String,
                      val result: String)
}