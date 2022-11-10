package com.example.datahentingtest.model

var kortListe = mutableListOf<Kort>()
const val KORT_ID = "kortExtra"

data class Kort (
    val brukerId: Int,
    val proveNavn: String
)