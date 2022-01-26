package id.deska.pcs.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import id.deska.pcs.MyApp.Companion.prefManager
import id.deska.pcs.data.response.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface MyApi {

    @JvmSuppressWildcards
    @Multipart
    @POST("auth/login")
    suspend fun authLogin(
        @PartMap postRegister: Map<String, RequestBody>
    ): Response<AuthResponse>

    @GET("product")
    suspend fun getProduct(
    ): Response<ProductResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST("product/{product_id}")
    suspend fun updateProduct(
        @Path("product_id") productId: String?,
        @PartMap dataProduct: Map<String, RequestBody>
    ): Response<ProductPostResponse>

    @DELETE("product/{product_id}")
    suspend fun deleteProduct(
        @Path("product_id") productId: String
    ): Response<ProductResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST("product")
    suspend fun postProduct(
        @PartMap dataProduct: Map<String, RequestBody>
    ): Response<ProductPostResponse>

    @GET("transaksi")
    suspend fun getTransaction(
    ): Response<TransactionResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST("transaksi")
    suspend fun postTransaction(
        @PartMap dataTransaction: Map<String, RequestBody>
    ): Response<TransactionPostResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://7033-114-125-92-61.ngrok.io/ci-pcs-rest-api/api/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
               .client(getOkHttpClient(networkConnectionInterceptor))
                .build()
                .create(MyApi::class.java)
        }

        private fun getOkHttpClient(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): OkHttpClient {
            val timeOut = 60L
            return OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
               .addInterceptor(networkConnectionInterceptor)
                .addInterceptor {
                    val req = it.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + prefManager.getAuthData()?.token)
                        .build()
                    return@addInterceptor it.proceed(req)
                }
                .build()
        }

        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
}