package id.deska.pcs.data.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(
    @SerializedName("data")
    val data: List<DataProduct>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

@Parcelize
data class DataProduct(
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
): Parcelable