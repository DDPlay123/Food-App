package com.side.project.foodapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.side.project.foodapp.R

class DialogManager(private val activity: Activity) {
    companion object {
        fun instance(activity: Activity): DialogManager = DialogManager(activity)
    }

    interface BottomCancelListener {
        fun response()
    }

    private var dialog: Dialog? = null
    private var loadingDialog: Dialog? = null
    private var bottomDialog: BottomSheetDialog? = null

    fun showCenterDialog(cancelable: Boolean, view: ViewBinding, keyboard: Boolean): ViewBinding {
        cancelCenterDialog()
        dialog = AlertDialog.Builder(activity).create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()

        if (keyboard) // 顯示鍵盤
            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        dialog?.setContentView(view.root)
        dialog?.setCancelable(cancelable)

        return view
    }

    fun showBottomDialog(view: ViewBinding, isFullExpand: Boolean, bias: Int = 0): ViewBinding {
        cancelBottomDialog()
        bottomDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        bottomDialog?.show()

        bottomDialog?.setContentView(view.root)
        expandBottomDialog(isFullExpand, bias)

        return view
    }

    fun setBottomCancelListener(bottomCancelListener: BottomCancelListener) =
        bottomDialog?.setOnCancelListener { bottomCancelListener.response() }

    private fun expandBottomDialog(isExpand: Boolean, bias: Int = 0) {
        if (!isExpand) return
        // 全展開
        val frameLayout: FrameLayout? = bottomDialog?.findViewById(
            com.google.android.material.R.id.design_bottom_sheet
        )
        if (frameLayout != null) {
            val bottomSheetBehavior: BottomSheetBehavior<View> =
                BottomSheetBehavior.from(frameLayout)
            (Resources.getSystem().displayMetrics.heightPixels - bias).let {
                bottomSheetBehavior.maxHeight = it
                bottomSheetBehavior.peekHeight = it
            }
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun cancelCenterDialog() = dialog?.dismiss()

    fun cancelLoadingDialog() = loadingDialog?.dismiss()

    fun cancelBottomDialog() = bottomDialog?.dismiss()

    fun cancelAllDialog() {
        dialog?.dismiss()
        loadingDialog?.dismiss()
        bottomDialog?.dismiss()
    }
}