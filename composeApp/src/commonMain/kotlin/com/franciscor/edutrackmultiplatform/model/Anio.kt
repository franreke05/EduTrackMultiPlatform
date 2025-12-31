package com.franciscor.edutrackmultiplatform.model

data class Anio(
    var id: String = "",
    var nombre: String = "",
    val descripcion: String = "",
    val fechaInicio: String = "",
    val fechaFin: String = "",
    var curso: Curso = Curso(),
)
