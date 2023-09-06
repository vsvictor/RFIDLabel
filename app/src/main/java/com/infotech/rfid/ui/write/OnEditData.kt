package com.infotech.rfid.ui.write

import java.util.UUID

interface OnEditData {
    fun generateID(uuid: UUID)
}