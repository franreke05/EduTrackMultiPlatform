package com.franciscor.edutrackmultiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class Notas(
    var id: String? = "",
    var id_asignatura: String? = "",
    val nombre: String? = "",
    val nota: Double? = 0.0,
    val porcentaje: Double? = 0.0,
    val fecha: String? = "",
    val periodo: Int? = 1,
)
