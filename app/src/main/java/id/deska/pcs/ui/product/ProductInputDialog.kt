package id.deska.pcs.ui.product

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deska.pcs.MyApp
import id.deska.pcs.data.response.DataProduct
import id.deska.pcs.databinding.DialogProductInputBinding
import id.deska.pcs.utils.*
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel


class ProductInputDialog(
    private val onSubmit: () -> Unit
): BaseDialogFragment() {

    private var _fragmentBinding: DialogProductInputBinding? = null
    private val binding get() = _fragmentBinding as DialogProductInputBinding

    private var product: DataProduct? = null
    private val viewModel: ProductViewModel by viewModel()
    private val mapProduct = HashMap<String, RequestBody>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentBinding = DialogProductInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViewCreated() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
    }

    private fun initView() {

        isCancelable = false
        arguments?.getParcelable<DataProduct>(PRODUCT).let {
            product = it
        }

        with(binding) {
            btnCancel.setOnClickListener{
                dismiss()
                onSubmit()
            }

            if (product != null) {
                etName.text = Editable.Factory.getInstance().newEditable(product!!.nama)
                etMessage.text = Editable.Factory.getInstance().newEditable(product!!.harga)
            }

            btnSubmit.setOnClickListener {
                doAddProduct()
            }
        }
    }

    private fun doAddProduct() {
        with(binding) {
            listOf<View>(
                tLName, tlMessage
            ).clearErrorInputLayout()

            if (etName.text.isNullOrEmpty()) {
                tLName.error = "tidak boleh kosong"
                return
            }

            if (etMessage.text.isNullOrEmpty()) {
                tlMessage.error = "tidak boleh kosong"
                return
            }

            val name = etName.text.toString()
            val price = etMessage.text.toString()

            mapProduct["nama"] = name.toMultipartForm()
            mapProduct["harga"] = price.toMultipartForm()
            mapProduct["admin_id"] = MyApp.prefManager.getAuthData()?.admin?.id!!.toMultipartForm()
            mapProduct["stock"] = "1".toMultipartForm()

            if (product != null) {
                viewModel.updateProduct(product!!.id!!, mapProduct)
            }else {
                viewModel.postProduct(mapProduct)
            }
        }
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ProductViewModel.ProductState.SuccesPost -> {
                    onSubmit()
                    dismiss()
                    binding.progress.hide()
                    context?.toast("Success..")
                }

                is ProductViewModel.ProductState.SuccesUpdate -> {
                    onSubmit()
                    dismiss()
                    binding.progress.hide()
                    context?.toast("Success..")
                }

                is ProductViewModel.ProductState.Error -> {
                    binding.progress.hide()
                    context?.toast("Gagal, Ulangii..")
                }
                is ProductViewModel.ProductState.Loading -> {
                    binding.progress.show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentBinding = null
    }

    companion object {
        val PRODUCT = "PRODUCT"

        fun build(product: DataProduct?, onSubmit: () -> Unit) = ProductInputDialog(onSubmit).apply {
            arguments = Bundle().apply {
                putParcelable(PRODUCT,product)
            }
        }
    }
}