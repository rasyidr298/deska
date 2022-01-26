package id.deska.pcs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.deska.pcs.data.response.TransactionData
import id.deska.pcs.databinding.ItemReportBinding

class ReportAdapter() : RecyclerView.Adapter<ReportAdapter.EventHolder>() {

    private val list = mutableListOf<TransactionData>()

    fun addList(listData : List<TransactionData>){
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
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(private val binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TransactionData) {
            with(binding){
                tvDate.text = data.tanggal
                tvId.text = "total : "+data.id
                tvPrice.text = "Rp. "+data.total
            }
        }
    }

}