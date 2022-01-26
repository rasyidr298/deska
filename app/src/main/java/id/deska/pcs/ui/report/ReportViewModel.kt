package id.deska.pcs.ui.report

import android.content.Context
import id.deska.pcs.data.network.MyApi
import id.deska.pcs.data.network.NetworkConnectionInterceptor
import id.deska.pcs.data.repository.AppRepository
import id.deska.pcs.data.response.TransactionResponse
import id.deska.pcs.utils.ApiException
import id.deska.pcs.utils.BaseViewModel
import id.deska.pcs.utils.Coroutines
import id.deska.pcs.utils.NoInternetException
import java.io.IOException


class ReportViewModel(context: Context): BaseViewModel<ReportViewModel.ReportState>() {

    private val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
    private val api = MyApi(networkConnectionInterceptor)
    private val repository = AppRepository(api)

    sealed class ReportState{
        data class Loading(val isLoading:Boolean) : ReportState()
        data class Succes(val product : TransactionResponse) : ReportState()
        data class Error(val message :String) : ReportState()
    }

    fun getTransaction() {
        Coroutines.main {
            _state.value= (ReportState.Loading(true))

            try {
                val barcodeResponse = repository.getTransaction()
                barcodeResponse.let {
                    _state.value= (ReportState.Succes(it))
                    return@main
                }

            } catch (e: ApiException) {
                _state.value= (ReportState.Error(e.message.toString()))
            } catch (e: NoInternetException) {
                _state.value= (ReportState.Error(e.message.toString()))
            }catch (e: IOException){
                _state.value= (ReportState.Error(e.message.toString()))
            }
        }
    }
}