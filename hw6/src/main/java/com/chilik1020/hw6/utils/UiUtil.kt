package com.chilik1020.hw6.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import com.chilik1020.hw6.R

fun showAlertDialog(
    ctx: Context,
    msg: Int,
    view: View?,
    positiveListener: DialogInterface.OnClickListener,
    negativeListener: DialogInterface.OnClickListener
) {
    val dialog = AlertDialog.Builder(ctx, R.style.AppCompatAlertDialogStyle)
        .setMessage(msg)
        .setPositiveButton(R.string.button_yes, positiveListener)
        .setNegativeButton(R.string.button_cancel, negativeListener)

    view?.let {
        (it.parent as? ViewGroup)?.removeView(it)
        dialog.setView(it)
    }
    dialog.create().show()
}