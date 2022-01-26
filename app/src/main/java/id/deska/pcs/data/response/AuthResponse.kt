package id.deska.pcs.data.response


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val data: DataAuth?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

data class DataAuth(
    @SerializedName("admin")
    val admin: Admin?,
    @SerializedName("token")
    val token: String?
)

data class Admin(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("password")
    val password: String?
)