package com.example.datahentingtest.kort

import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.model.Post

interface PostClickListener {
    fun onClick(Post: Post)
}