package id.deska.pcs.di

import id.deska.pcs.ui.login.AuthViewModel
import id.deska.pcs.ui.product.ProductViewModel
import id.deska.pcs.ui.report.ReportViewModel
import id.deska.pcs.ui.transaction.TransactionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { ProductViewModel(get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { TransactionViewModel(get()) }
}