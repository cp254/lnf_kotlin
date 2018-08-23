package io.ginius.cp.kt.lostfound.kotlin

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import io.ginius.cp.kt.lostfound.R

class PositiveDialog(context: Context, msg: String) : BaseDialogHelper() {

    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.success_dialog, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    //  notes edit text
    val eText: TextView by lazy {
        dialogView.findViewById<TextView>(R.id.msg_text)
    }

    fun setMessage(msg: String) {
        eText.text = msg
    }

    //  done icon
    private val doneButton: Button by lazy {
        dialogView.findViewById<Button>(R.id.btn_next)
    }


    //  closeIconClickListener with listener
    fun closeButtonClickListener(func: (() -> Unit)? = null) =
            with(doneButton) {
                setClickListenerToDialogButton(func)
            }


    //  view click listener as extension function
    private fun View.setClickListenerToDialogButton(func: (() -> Unit)?) =
            setOnClickListener {
                func?.invoke()
                dialog?.dismiss()
            }
}