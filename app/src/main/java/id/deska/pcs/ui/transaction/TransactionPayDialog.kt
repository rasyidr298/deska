package id.deska.pcs.ui.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deska.pcs.MyApp
import id.deska.pcs.MyApp.Companion.prefManager
import id.deska.pcs.databinding.DialogTransactionPayBinding
import id.deska.pcs.utils.*
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel


class TransactionPayDialog(
    private val onSubmit: () -> Unit
): BaseDialogFragment() {

    private val mapTransaction = HashMap<String, RequestBody>()
    private val viewModel: TransactionViewModel by viewModel()

    private var _fragmentBinding: DialogTransactionPayBinding? = null
    private val binding get() = _fragmentBinding as DialogTransactionPayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentBinding = DialogTransactionPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViewCreated() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupView()
        observeData()
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner, { result ->
            when (result) {
                is TransactionViewModel.TransactionState.SuccesPost -> {
                    activity?.toast("success")
                    binding.progress.hide()
                    dismiss()
                }

                is TransactionViewModel.TransactionState.Error -> {
                    binding.progress.hide()
                }
                is TransactionViewModel.TransactionState.Loading -> {
                    binding.progress.show()
                }
            }
        })
    }

    private fun initView() {

        isCancelable = false

        with(binding) {
            btnCancel.setOnClickListener{
                dismiss()
                onSubmit()
            }

            btnSubmit.setOnClickListener {
                if (tvTotal.text.toString().toInt() > etName.text.toString().toInt()) {
                    activity?.toast("uang anda kurang, silahkan berkerja lebih giat lagi!!")
                }else if (tvTotal.text.isEmpty()) {
                    activity?.toast("harusa terisi")
                }else {
                    addTransaction()
                }
            }
        }
    }

    private fun addTransaction() {
        with(binding) {
            listOf<View>(
                tilNik
            ).clearErrorInputLayout()

            if (etName.text.isNullOrEmpty()) {
                tilNik.error = "tidak boleh kosong"
                return
            }

            val name = etName.text.toString()

            mapTransaction["admin_id"] = prefManager.getAuthData()!!.admin!!.id!!.toMultipartForm()
            mapTransaction["total"] = name.toMultipartForm()

            viewModel.postTransaction(mapTransaction)
        }
    }

    private fun setupView() {
        with(binding) {
            val totalPrice = prefManager.spTotalPrice

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.isNotEmpty()) {
                        tvPenerimaan.text = (s.toString().toInt() - totalPrice).toString()
                    }else {
                        tvPenerimaan.text = (0 - totalPrice).toString()
                    }
                }
            })

            tvTotal.text = totalPrice.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentBinding = null
    }

    companion object {
        fun build(onSubmit: () -> Unit) = TransactionPayDialog(onSubmit)
    }
}