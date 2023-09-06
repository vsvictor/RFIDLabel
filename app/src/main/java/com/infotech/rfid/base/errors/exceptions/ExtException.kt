package com.infotech.rfid.base.errors

import java.lang.Exception

data class ExtException(val code: Int, val err: ErrorBody): Exception() {

}