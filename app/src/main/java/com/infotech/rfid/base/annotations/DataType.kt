package com.infotech.rfid.base.annotations

import kotlin.reflect.KClass

@kotlin.annotation.Retention
@kotlin.annotation.Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DataType(val type: KClass<*>)
