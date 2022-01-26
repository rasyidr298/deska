package id.deska.pcs.data.network

import android.util.Log
import id.deska.pcs.utils.ApiException
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            Log.d("succes:", response.toString())
            return response.body()!!
        } else {
            Log.d("failed:", response.toString())
            val message = StringBuilder()
            message.append(response.code())
            throw ApiException(message.toString())
        }
    }
}