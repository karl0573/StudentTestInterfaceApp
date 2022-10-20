package com.example.mobprosjekt

var proveListe = mutableListOf<proveJson>()

data class proveJson(
    val records: List<Record>
)