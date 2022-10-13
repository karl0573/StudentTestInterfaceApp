package com.example.mobprosjekt

import com.squareup.moshi.Json

var proveListe = mutableListOf<Prove>()

data class Prove (
    var bilde: Int,
    var bruker: String,
    @Json(name = "proveNavn")
    var tittel:String,
val id: Int? = proveListe.size

)
