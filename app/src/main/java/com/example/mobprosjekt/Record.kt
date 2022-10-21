package com.example.mobprosjekt

import android.provider.ContactsContract.Data
import java.util.*
var proveListe = mutableListOf<Record>()

data class Record(
    val brukerId: Int,
    val proveNavn: String
)