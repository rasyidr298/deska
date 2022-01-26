package id.deska.pcs.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffCallback(
    private var oldList : List<Any>,
    private var newList : List<Any>
) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
//        return (oldList[oldItemPosition].id == newList[newItemPosition].id)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}