package com.ma.development.todo_app.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.ma.development.todo_app.databinding.CustomDiaologLayoutBinding

fun showDeleteDialog(
    context: Context,
    layoutInflater: LayoutInflater,
    positiveBtnHandler: () -> Unit,
) {
    val customDialogViewBinding = CustomDiaologLayoutBinding.inflate(layoutInflater)
    val dialog = AlertDialog.Builder(context).apply {
        setView(customDialogViewBinding.root)
    }.create()
    dialog.show()
    customDialogViewBinding.dialogPositiveBtn.setOnClickListener {
        positiveBtnHandler()
        dialog.dismiss()
    }
    customDialogViewBinding.dialogNegativeBtn.setOnClickListener { dialog.dismiss() }


}



fun showSnackBar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, length).setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).show()
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}
