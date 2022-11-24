package com.example.datahentingtest.dataklasser

var kortListe = mutableListOf<Kort>()
var posterListe = mutableListOf<Kort>()
const val KORT_ID = "kortExtra"

data class Kort (
    val brukerId: Int,
    val proveNavn: String
)
