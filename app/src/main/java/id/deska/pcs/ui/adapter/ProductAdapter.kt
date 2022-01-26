package id.deska.pcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.deska.pcs.data.response.DataProduct
import id.deska.pcs.databinding.ItemProductBinding
import id.deska.pcs.utils.OnItemClicked

class ProductAdapter(private val onItemClicked: OnItemClicked) :
    RecyclerView.Adapter<ProductAdapter.EventHolder>() {

    private val list = mutableListOf<DataProduct>()

    fun addList(listData: List<DataProduct>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            DiffCallback(
                list,
                listData
            )
        )
        list.clear()
        list.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(onItemClicked, list[position])
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClicked: OnItemClicked, data: DataProduct) {
            with(binding) {
                tvName.text = data.nama
                tvPrice.text = "Rp." + data.harga
                btnEdit.setOnClickListener {
                    onItemClicked.onUpdateProductClick(data)
                }
                btnDelete.setOnClickListener {
                    onItemClicked.onDeleteProductClick(data)
                }
            }
        }
    }

}