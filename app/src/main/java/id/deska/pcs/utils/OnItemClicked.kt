package id.deska.pcs.utils

import id.deska.pcs.data.response.DataProduct

interface OnItemClicked {
    fun onIncreaseTransaction(total: Int, position: Int){}
    fun onDecreaseTransaction(total: Int, position: Int){}
    fun onDeleteProductClick(data: DataProduct){}
    fun onUpdateProductClick(data: DataProduct){}
}