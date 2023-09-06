package com.infotech.rfid.data.model

class ProfileData(override val firstName: String, override val lastName: String, override val middleName: String? = null, var crewID: String? = null, var canBeHead: Boolean = false, var userID: String? = null, var isHead: Boolean = false): PersonData(firstName,lastName, middleName)