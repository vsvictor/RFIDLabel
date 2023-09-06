package com.infotech.rfid.ui.common

interface OnBottomBarVisible {
    fun onBottomBarVisible(visible: Boolean)
    fun navigationBarHeight(): Int
    fun bottomBarHeight(): Int
}