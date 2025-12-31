package com.franciscor.edutrackmultiplatform.model

data class Curso(
    var id: String = "",
    val nombre: String = "",
    val asignaturas: MutableList<Asignatura> = mutableListOf(),
)
