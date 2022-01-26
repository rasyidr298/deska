package id.deska.pcs.ui.product

import android.content.Context
import id.deska.pcs.data.network.MyApi
import id.deska.pcs.data.network.NetworkConnectionInterceptor
import id.deska.pcs.data.repository.AppRepository
import id.deska.pcs.data.response.DataProduct
import id.deska.pcs.data.response.ProductPostResponse
import id.deska.pcs.utils.ApiException
import id.deska.pcs.utils.BaseViewModel
import id.deska.pcs.utils.Coroutines
import id.deska.pcs.utils.NoInternetException
import okhttp3.RequestBody
import java.io.IOException


class ProductViewModel(context: Context): BaseViewModel<ProductViewModel.ProductState>() {

    private val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
    private val api = MyApi(networkConnectionInterceptor)
    private val repository = AppRepository(api)

    sealed class ProductState{
        data class Loading(val isLoading:Boolean) : ProductState()
        data class Succes(val product: List<DataProduct>) : ProductState()
        data class SuccesPost(val product : ProductPostResponse) : ProductState()
        data class SuccesUpdate(val product : ProductPostResponse) : ProductState()
        data class SuccesDelete(val product: List<DataProduct>) : ProductState()
        data class Error(val message :String) : ProductState()
    }

    fun getProduct() {
        Coroutines.main {
            _state.value= (ProductState.Loading(true))

            try {
                val barcodeResponse = repository.getProduct()
                barcodeResponse.let {
                    _state.value= (ProductState.Succes(it.data!!.sortedBy { it.nama }))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (ProductState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (ProductState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (ProductState.Error(e.message.toString()))
            }
        }
    }

    fun postProduct(dataProduct: HashMap<String, RequestBody>) {
        Coroutines.main {
            _state.value= (ProductState.Loading(true))

            try {
                val barcodeResponse = repository.postProduct(dataProduct)
                barcodeResponse.let {
                    _state.value= (ProductState.SuccesPost(it))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (ProductState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (ProductState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (ProductState.Error(e.message.toString()))
            }
        }
    }

    fun deleteProduct(productId: String) {
        Coroutines.main {
            _state.value= (ProductState.Loading(true))

            try {
                val barcodeResponse = repository.deleteProduct(productId)
                barcodeResponse.let {
                    _state.value= (ProductState.SuccesDelete(it.data!!.sortedBy { it.nama }))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (ProductState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (ProductState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (ProductState.Error(e.message.toString()))
            }
        }
    }

    fun updateProduct(productId: String, dataProduct: HashMap<String, RequestBody>) {
        Coroutines.main {
            _state.value= (ProductState.Loading(true))

            try {
                val barcodeResponse = repository.updateProduct(productId, dataProduct)
                barcodeResponse.let {
                    _state.value= (ProductState.SuccesUpdate(it))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (ProductState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (ProductState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (ProductState.Error(e.message.toString()))
            }
        }
    }

}