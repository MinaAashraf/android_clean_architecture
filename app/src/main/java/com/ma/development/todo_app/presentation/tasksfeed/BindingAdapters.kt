package com.ma.development.todo_app.presentation.tasksfeed

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.ma.development.todo_app.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("loading")
fun bindLoader(progressBar: LinearProgressIndicator, loading: Boolean) {
    if (loading)
        progressBar.visibility = View.VISIBLE
    else
        progressBar.visibility = View.GONE
}

@BindingAdapter("completion")
fun bindCompletionText(textView: TextView, isCompleted: Boolean) {
    textView.text =
        if (isCompleted) textView.context.getString(R.string.completed_label)
        else textView.context.getString(R.string.inComplete_label)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun bindDate(textView: TextView, myDateString: String) {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
    val formatter = SimpleDateFormat("dd, MMM 'at' hh:mm aaa")
    val date: Date? = parser.parse(myDateString)
    textView.text = formatter.format(date!!).toString()
}