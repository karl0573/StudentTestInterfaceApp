package com.example.mobprosjekt
import java.util.*
import com.squareup.moshi.Json

var proveListe = mutableListOf<Prove>()

data class Prove (
    val brukerId: Int,
    val proveNavn: String,
val id: Int? = proveListe.size

)
