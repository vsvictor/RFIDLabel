package com.infotech.artprt.ui.login.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.infotech.rfid.R
import com.infotech.rfid.ui.common.OnUnderstand

class LoginErrorDialog(val model: OnUnderstand, val callback:()->Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), R.style.StyleDialogRoundedCorner)
        val inflater = requireActivity().layoutInflater;
        val view = inflater.inflate(R.layout.fragment_login_error, null)

        view.findViewById<AppCompatTextView>(R.id.tvUnderstand).setOnClickListener {
            model.onUnderstand()
            dialog?.cancel()
        }
        builder.setView(view).setCancelable(false)
        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        callback.invoke()
    }
}