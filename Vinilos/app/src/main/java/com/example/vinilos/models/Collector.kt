package com.example.vinilos.models

data class Collector (
    val id: Int,
    val name:String,
    val telephone:String,
    val email:String,
    val albums: List<AlbumSummary>?,
    val performers: List<Performer>?
)