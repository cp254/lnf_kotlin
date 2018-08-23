package io.ginius.cp.kt.lostfound.kotlin

import com.google.gson.annotations.SerializedName


data class SearchDocumentResponse(
        @SerializedName("statuscode") var statuscode: Int, // 0
        @SerializedName("statusname") var statusname: String, // success
        @SerializedName("result") var result: Result
) {

    data class Result(
            @SerializedName("doc") var doc: Doc,
            @SerializedName("doc_images") var docImages: List<DocImage>
    ) {

        data class DocImage(
                @SerializedName("_id") var id: String, // 5b43d32267d77a7c81310610
                @SerializedName("doc_ref") var docRef: String, // 13
                @SerializedName("image_name") var imageName: String, // Test_Test_332211
                @SerializedName("image_path") var imagePath: String, // /images/Test_Test_332211.png
                @SerializedName("uploadby") var uploadby: String, // 8
                @SerializedName("uploaddate") var uploaddate: String, // 2018-07-09T21:26:58.158Z
                @SerializedName("image_id") var imageId: Int, // 8
                @SerializedName("__v") var v: Int // 0
        )


        data class Doc(
                @SerializedName("_id") var id: String, // 5b43d31367d77a7c8131060f
                @SerializedName("location") var location: Location,
                @SerializedName("doc_unique_id") var docUniqueId: String, // 332211
                @SerializedName("doc_type") var docType: String, // NATIONAL_ID
                @SerializedName("doc_name") var docName: String, // NATIONAL_ID
                @SerializedName("doc_details") var docDetails: String,
                @SerializedName("doc_fname") var docFname: String, // Test
                @SerializedName("doc_lname") var docLname: String, // Test
                @SerializedName("doc_num") var docNum: Int // 13
        ) {

            data class Location(
                    @SerializedName("coordinates") var coordinates: List<Double>,
                    @SerializedName("type") var type: String // Point
            )
        }
    }
}