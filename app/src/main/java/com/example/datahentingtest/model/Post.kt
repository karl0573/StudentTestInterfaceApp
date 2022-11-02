package com.example.datahentingtest.model

var postListe = mutableListOf<Post>()

data class Post (
    val brukerId: Int,
    val proveNavn: String
)