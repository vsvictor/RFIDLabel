package com.infotech.rfid.ui.write

import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.infotech.rfid.OnClear
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.data.model.Entity
import com.infotech.rfid.databinding.FragmentReadBinding
import com.infotech.rfid.di.provideGSON
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.Arrays
import kotlin.experimental.and

@Layout(R.layout.fragment_read)
class ReadFragment : BaseFragment<FragmentReadBinding, ReadViewModel>(){
    private val TAG = ReadFragment::class.java.simpleName
    private var onClear: OnClear? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edID.isEnabled = false
        binding.edName.isEnabled = false
        binding.edComment.isEnabled = false
        requireActivity().onBackInvokedDispatcher.unregisterOnBackInvokedCallback {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //requireActivity().onBackPressedDispatcher.addCallback(this)
        binding.model = viewModel
    }
    override fun onResume() {
        super.onResume()
        val isEnable = requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()
        Log.d(TAG, "on Resume, InBackPressedCallBack is "+isEnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
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
        intent?.let {
            readFromIntent(it)
        }
    }
    private fun readFromIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val myTag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var msgs = mutableListOf<NdefMessage>()
            if (rawMsgs != null) {
                for (i in rawMsgs.indices) {
                    msgs.add(i, rawMsgs[i] as NdefMessage)
                }
                buildTagViews(msgs.toTypedArray())
            }
        }
    }
    private fun buildTagViews(msgs: Array<NdefMessage>) {
        if (msgs == null || msgs.isEmpty()) return
        var text = ""
        val payload = msgs[0].records[0].payload
        val textEncoding: Charset = if ((payload[0] and 128.toByte()).toInt() == 0) Charsets.UTF_8 else Charsets.UTF_16 // Get the Text Encoding
        val languageCodeLength: Int = (payload[0] and 51).toInt() // Get the Language Code, e.g. "en"
        try {
            // Get the Text
            text = String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                textEncoding
            )
        } catch (e: UnsupportedEncodingException) {
            Log.e("UnsupportedEncoding", e.toString())
        }
        val res = "Message read from NFC Tag:\n $text"
        val gson = provideGSON()
        val data = gson.fromJson<Entity>(text, Entity::class.java)
        binding.edID.setText(data.id.toString())
        binding.edName.setText(data.name)
        binding.edComment.setText(data.comment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "OnBack")
    }
}