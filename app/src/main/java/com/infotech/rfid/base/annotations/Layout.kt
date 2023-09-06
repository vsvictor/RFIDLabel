package com.infotech.rfid.base.annotations

import androidx.annotation.LayoutRes

@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Layout(@LayoutRes val id: Int)