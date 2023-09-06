package com.infotech.rfid.base.errors

import com.infotech.rfid.data.api.jstp.JSTPError

class ErrorData(var code: Int = -1, val body: ErrorBody = ErrorBody("Undefined error")){
        constructor(err:JSTPError):this(err.err, ErrorBody(err.name))
}