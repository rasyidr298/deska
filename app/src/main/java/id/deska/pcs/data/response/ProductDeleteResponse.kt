package id.deska.pcs.data.response


import com.google.gson.annotations.SerializedName

data class ProductDeleteResponse(
    @SerializedName("data")
    val data: List<DataDeleteProduct>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

data class DataDeleteProduct(
    @SerializedName("admin_id")
    val adminId: String?,
    @SerializedName("harga")
    val harga: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("stock")
    val stock: String?
)