package id.deska.pcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.deska.pcs.data.response.DataProduct
import id.deska.pcs.databinding.ItemTransactionBinding
import id.deska.pcs.utils.OnItemClicked

class TransactionAdapter(private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<TransactionAdapter.EventHolder>() {

    val list = mutableListOf<DataProduct>()

    fun addList(listData : List<DataProduct>){
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(
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
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(onItemClicked, list[position], position)
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClicked: OnItemClicked, data: DataProduct, position: Int) {
            with(binding){
                tvName.text = data.nama
                tvPrice.text = "Rp."+data.harga

                var total = 0
                tvTotal.text = "0"

                btnIncrease.setOnClickListener {
                    total += 1
                    onItemClicked.onIncreaseTransaction(total, position)
                    tvTotal.text = total.toString()
                }

                btnDecrease.setOnClickListener {
                    if (total != 0) {
                        total -= 1
                        onItemClicked.onDecreaseTransaction(total, position)
                        tvTotal.text = total.toString()
                    }
                }
            }
        }
    }
}