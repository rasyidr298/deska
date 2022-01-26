package id.deska.pcs.data.response


import com.google.gson.annotations.SerializedName

data class TransactionPostResponse(
    @SerializedName("data")
    val data: TransactionData,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)