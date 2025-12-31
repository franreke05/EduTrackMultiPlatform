package com.franciscor.edutrackmultiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class Asignatura(
    var id: String? = "",
    val nombre: String? = "",
    val creditos: Int? = 0,
    val descripcion: String? = "",
    val lista_notas: MutableList<Notas>? = mutableListOf(),
    val media: Double? = 0.0,
    val id_usuario: String? = "",
    val id_anio: String? = "",
    val tipo_periodo: String? = "Trimestre",
    val numero_periodos: Int? = 3,
)
