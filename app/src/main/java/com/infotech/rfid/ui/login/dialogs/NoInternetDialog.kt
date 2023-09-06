package com.infotech.rfid.ui.login.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.infotech.rfid.R
import com.infotech.rfid.ui.common.OnUnderstand

class NoInternetDialog(val model: OnUnderstand) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), R.style.StyleDialogRoundedCorner)
        val inflater = requireActivity().layoutInflater;
        val view = inflater.inflate(R.layout.fragment_no_internet, null)

        view.findViewById<AppCompatTextView>(R.id.tvUnderstand).setOnClickListener {
            model.onUnderstand()
            dialog?.cancel()
        }
        builder.setView(view).setCancelable(false)
        return builder.create()
    }
}