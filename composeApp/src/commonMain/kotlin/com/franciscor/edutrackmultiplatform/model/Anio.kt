package com.franciscor.edutrackmultiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class Anio(
    var id: String? = "",
    var nombre: String? = "",
    val descripcion: String? = "",
    val fechaInicio: String? = "",
    val fechaFin: String? = "",
    var numero_asignaturas: Int? = 0,
    var lista_asignaturas: Map<String, Asignatura>? = emptyMap(),
    val id_user: String? = "",
)
