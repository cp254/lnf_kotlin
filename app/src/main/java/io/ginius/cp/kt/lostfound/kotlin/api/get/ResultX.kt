package io.ginius.cp.kt.lostfound.kotlin.api.get

data class ResultX(
        val MerchantRequestID: String,
        val CheckoutRequestID: String,
        val ResponseCode: String,
        val ResponseDescription: String,
        val CustomerMessage: String
)