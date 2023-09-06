package com.infotech.rfid.data.api.jstp

enum class JSTPError(val err:Int) {
        NOT_AUTHENTICATED(1301),
        LOGOFF_TIMEOUT(1302),
        CHANGE_PASSWORD(1303),
        WRONG_PASSWORD(1304),
        ACCOUNT_BLOCKED(1305),
        APP_NOT_AVAIL(1306),
        ACTION_NOT_PERMITTED(1307),
        CATEGORY_NOT_PERMITTED(1308),
        SAME_PASSWORD(1309),
        WEAK_PASSWORD(1310),
        REQUIRED_SMART_CARD(1311),
        MAX_SESSIONS_EXCEEDED(1312),
        ERR_INTERNAL_API_ERROR(16),
        ERR_INVALID_SIGNATURE(17),
        PROFILE_PARSE_ERROR(1500),
        UNDEFINED(10000);

        companion object {
                private val orders = JSTPError.values().associateBy(JSTPError::err)
                infix fun from(order: Int): JSTPError = orders[order] ?: JSTPError.UNDEFINED
                infix fun to(error: JSTPError):Int = error.err
        }

}