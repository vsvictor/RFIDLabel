package com.infotech.rfid.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.base.interfaces.*
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import java.lang.reflect.ParameterizedType


open class BaseBottomFragment<DB : ViewDataBinding, VM : BaseViewModel<*>> : BottomSheetDialogFragment(),
    OnLoader, OnInitViewModel, OnKeyboardStateChanged, OnMessage {
    private val TAG = BaseBottomFragment::class.java.simpleName
    val vmClass = (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments[1] as Class<VM>
    protected lateinit var binding: DB
    protected val viewModel by sharedViewModel(this, vmClass)
    protected var navController: NavController? = null
    protected var factory: ViewModelProvider.Factory? = null
    private var lastContentHeight = 0

    protected var onActionBarState: OnActionBarState? = null
    protected var onKeyboadVisibleChanged: OnKeyboadVisibleChanged? = null
    protected var onLoader: OnLoader? = null
    private var toast:Toast? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = javaClass.getAnnotation(Layout::class.java)
        binding = DataBindingUtil.inflate(inflater, layout!!.id, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        try {
            onLoader = context as OnLoader
        }catch (ex: Exception){}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.onInitViewModel = this
        viewModel.onMessage = this
        viewModel.setOnLoaderListener(this)
        navController = findNavController()// Navigation.findNavController(binding.root)
        lifecycle.addObserver(viewModel)
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                onError(it)
                viewModel.clearError()
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onKeyboadVisibleChanged = context as OnKeyboadVisibleChanged
        onActionBarState = context as OnActionBarState
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
        onLoader = null
    }

    override fun onDetach() {
        super.onDetach()
        //onLoader = null
        onKeyboadVisibleChanged = null
        onActionBarState = null
        show(false)
    }
    open fun onBackPressed() {
        viewModel.onBackPressed()
    }

    open fun onNavigationOnClickListener(){
        onBackPressed()
    }
    open fun onError(data: ErrorData){
        toast?.cancel()
        toast = null
        val builder = StringBuilder()
        builder.append(data.body.message)
        builder.append("\n")
        for(err in data.body.errs){
            builder.append(err.first)
            builder.append(" : ")
            builder.append(err.second)
            builder.append("\n")
        }
        Log.d(TAG, "Error: "+builder.toString())
        toast = Toast.makeText(requireContext(), builder.toString(), Toast.LENGTH_SHORT)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.show()
    }

    override fun inShowed(): Boolean {
        return onLoader?.inShowed()?:false
    }

    override fun show(isShow: Boolean) {
        onLoader?.show(isShow)
    }

    override fun onInitViewModel() {
        viewModel.initNavController(navController)
    }

    override fun onKeyboardStateChanged(isVisible: Boolean) {}

    override fun onMessage(textID: Int) {
        Toast.makeText(requireContext(), textID, Toast.LENGTH_LONG).show()
    }
}
