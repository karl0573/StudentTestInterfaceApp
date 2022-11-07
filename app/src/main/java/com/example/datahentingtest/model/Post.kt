package com.example.datahentingtest.model

var posterListe = mutableListOf<Post>()

data class Post (
    val brukerId: Int,
    val proveNavn: String
)