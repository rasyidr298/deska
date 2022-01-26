package id.deska.pcs.data.response


import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    val data: List<TransactionData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

data class TransactionData(
    @SerializedName("admin_id")
    val adminId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("tanggal")
    val tanggal: String?,
    @SerializedName("total")
    val total: String?
)