package com.franciscor.edutrackmultiplatform.model

data class Usuario(
    var id: String = "",
    var nombre: String = "",
    var email: String = "",
    var password: String = "",
    val anio: MutableList<Anio> = mutableListOf(),
)
