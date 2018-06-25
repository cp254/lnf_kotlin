package io.ginius.cp.kt.lostfound.kotlin.api.get


data class MpesaPayment(
        val statuscode: Int,
        val statusname: String,
        val result: Result
) {

    data class Result(
            val MerchantRequestID: String,
            val CheckoutRequestID: String,
            val ResponseCode: String,
            val ResponseDescription: String,
            val CustomerMessage: String
    )

//    {
//        "statuscode": 0,
//        "statusname": "success",
//        "result": {
//        "MerchantRequestID": "7680-288991-1",
//        "CheckoutRequestID": "ws_CO_DMZ_38808258_10062018112659889",
//        "ResponseCode": "0",
//        "ResponseDescription": "Success. Request accepted for processing",
//        "CustomerMessage": "Success. Request accepted for processing"
//    }
//    }
}