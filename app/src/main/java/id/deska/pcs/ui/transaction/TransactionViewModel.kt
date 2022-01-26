package id.deska.pcs.ui.transaction

import android.content.Context
import id.deska.pcs.data.network.MyApi
import id.deska.pcs.data.network.NetworkConnectionInterceptor
import id.deska.pcs.data.repository.AppRepository
import id.deska.pcs.data.response.TransactionPostResponse
import id.deska.pcs.utils.ApiException
import id.deska.pcs.utils.BaseViewModel
import id.deska.pcs.utils.Coroutines
import id.deska.pcs.utils.NoInternetException
import okhttp3.RequestBody
import java.io.IOException


class TransactionViewModel(context: Context): BaseViewModel<TransactionViewModel.TransactionState>() {

    private val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
    private val api = MyApi(networkConnectionInterceptor)
    private val repository = AppRepository(api)

    sealed class TransactionState{
        data class Loading(val isLoading:Boolean) : TransactionState()
        data class SuccesPost(val product : TransactionPostResponse) : TransactionState()
        data class Error(val message :String) : TransactionState()
    }

    fun postTransaction(dataTransaction: HashMap<String, RequestBody>) {
        Coroutines.main {
            _state.value= (TransactionState.Loading(true))

            try {
                val barcodeResponse = repository.postTransaction(dataTransaction)
                barcodeResponse.let {
                    _state.value= (TransactionState.SuccesPost(it))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (TransactionState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (TransactionState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (TransactionState.Error(e.message.toString()))
            }
        }
    }

}