package io.ginius.cp.kt.lostfound.kotlin

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import io.ginius.cp.kt.lostfound.R

class ConfigurableDialogs {
    fun dialogErrorConfig(activity: Activity, msg: String?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
//        if (msg != null)
//            msg_text.text = msg
//        btn_next.setOnClickListener {
//            dialog.dismiss()
//        }
        dialog.show()
    }
}