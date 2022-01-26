package id.deska.pcs.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import android.content.DialogInterface




fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun tag(context: Context) : String {
    return context.javaClass.simpleName
}

fun enableClick(view: View) {
    with(view) {
        isFocusable = true
        isClickable = true
        isEnabled = true
    }
}

fun disableClick(view: View) {
    with(view) {
        isFocusable = false
        isClickable = false
        isEnabled = false
    }
}

fun List<View>.clearErrorInputLayout(){
    forEach { vi ->
        if(vi is TextInputLayout){
            vi.isErrorEnabled = false
        }
    }
}

fun dialog(context: Context, onSubmit: () -> Unit) {
    val dialogClickListener =
        DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    onSubmit.invoke()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                }
            }
        }

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
        .setNegativeButton("No", dialogClickListener).show()
}