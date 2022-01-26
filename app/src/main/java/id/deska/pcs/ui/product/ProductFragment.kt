package id.deska.pcs.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.deska.pcs.data.response.DataProduct
import id.deska.pcs.databinding.FragmentProductBinding
import id.deska.pcs.ui.adapter.ProductAdapter
import id.deska.pcs.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment : Fragment(), OnItemClicked {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var adapter: ProductAdapter

    private var _fragment: FragmentProductBinding? = null
    private val binding get() = _fragment as FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragment = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    private fun initView() {
        adapter = ProductAdapter(this)

        with(binding.rvProduct) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProductFragment.adapter
        }

        viewModel.getProduct()

        with(binding) {
            btnAdd.setOnClickListener {
                ProductInputDialog.build(null) {
                    viewModel.getProduct()
                }.show(requireFragmentManager(), tag(requireContext()))
            }
        }
    }

    override fun onDeleteProductClick(data: DataProduct) {
        super.onDeleteProductClick(data)
        dialog(requireContext()) {
            viewModel.deleteProduct(data.id!!)
        }
    }

    override fun onUpdateProductClick(data: DataProduct) {
        super.onUpdateProductClick(data)
        ProductInputDialog.build(data) {
            viewModel.getProduct()
        }.show(requireFragmentManager(), tag(requireContext()))
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ProductViewModel.ProductState.Succes -> {
                    this.adapter.addList(result.product)
                    binding.let {
                        it.progress.hide()
                        it.tvTotal.text = result.product.size.toString()

                        //if empty
                        if (result.product.isNullOrEmpty()) {
                            activity?.toast("data kosong")
                            it.progress.hide()
                        }
                    }
                }

                is ProductViewModel.ProductState.SuccesDelete -> {
                    activity?.toast("succes delete")
                    this.adapter.addList(result.product)
                    binding.let {
                        it.tvTotal.text = result.product.size.toString()
                        it.progress.hide()
                    }
                }

                is ProductViewModel.ProductState.Error -> {
                    binding.let {
                        activity?.toast(result.message)
                        it.progress.show()
                    }
                }
                is ProductViewModel.ProductState.Loading -> {
                    binding.progress.show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragment = null
    }
}