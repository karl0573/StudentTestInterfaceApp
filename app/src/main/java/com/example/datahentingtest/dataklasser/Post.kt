package com.example.datahentingtest.dataklasser

var posterListe = mutableListOf<Post>()

data class Post (
    val brukerId: Int,
    val proveNavn: String
)