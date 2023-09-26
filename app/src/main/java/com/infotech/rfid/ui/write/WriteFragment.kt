package com.infotech.rfid.ui.write

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.infotech.rfid.OnClear
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.data.model.Entity
import com.infotech.rfid.databinding.FragmentWriteBinding
import com.infotech.rfid.di.provideGSON
import java.nio.charset.Charset
import java.util.Locale
import java.util.UUID


@Layout(R.layout.fragment_write)
class WriteFragment : BaseFragment<FragmentWriteBinding, WriteViewModel>(), OnEditData {
    private val TAG = WriteFragment::class.java.simpleName
    private var onClear: OnClear? = null
    private var counter = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClear = context as OnClear
    }

    override fun onDetach() {
        onClear = null
        super.onDetach()
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val detectedTag: Tag? = intent!!.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        val id = UUID.fromString(binding.edID.text.toString())
        val name = binding.edName.text.toString()
        val comment = binding.edComment.text.toString()
        val entity = Entity(id, name, comment)
        val gson = provideGSON()
        val str = gson.toJson(entity, Entity::class.java)
        val rec = makeTextRecord(str)
        val ndefMessage = NdefMessage(rec)
        writeTag(ndefMessage, detectedTag)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.onEditData = this
        binding.model = viewModel
    }
    fun makeTextRecord(text: String): NdefRecord? {
        val lang: String = Locale.getDefault().language
        val langBytes: ByteArray = lang.toByteArray(Charset.forName("US-ASCII"))
        val utfEncoding = Charset.forName("UTF-8")
        val textBytes = text.toByteArray(utfEncoding)
        val utfBit = 0
        val status = (utfBit + langBytes.size).toChar()
        val data = ByteArray(1 + langBytes.size + textBytes.size)
        data[0] = status.code.toByte()
        System.arraycopy(langBytes, 0, data, 1, langBytes.size)
        System.arraycopy(textBytes, 0, data, 1 + langBytes.size, textBytes.size)
        return NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_TEXT, ByteArray(0), data
        )
    }
    open fun writeTag(ndefMessage: NdefMessage?, tag: Tag?){
        try {
            counter++
            val ndef = Ndef.get(tag)
            ndef.connect()
            Log.d(TAG, "Count:"+counter)
            ndef.writeNdefMessage(ndefMessage)
            ndef.close()
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun generateID(uuid: UUID) {
        binding.edID.setText(uuid.toString())
    }
}