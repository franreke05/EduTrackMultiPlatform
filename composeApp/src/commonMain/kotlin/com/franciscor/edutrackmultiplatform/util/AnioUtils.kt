package com.franciscor.edutrackmultiplatform.util

import com.franciscor.edutrackmultiplatform.model.Anio
import com.franciscor.edutrackmultiplatform.model.Notas
import kotlin.math.abs
import kotlin.math.round

fun toRoman(num: Int): String {
    val values = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val symbols = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var number = num
    val result = StringBuilder()
    for (i in values.indices) {
        while (number >= values[i]) {
            number -= values[i]
            result.append(symbols[i])
        }
    }
    return result.toString()
}

fun comprobarCampos(num: String, nombre: String, fechaInicio: String, fechaFin: String): Boolean {
    var comprobado = false
    if (num < 0.toString() || num > 21.toString()) {
        if (nombre.isNotEmpty()) {
            if (fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                if (fechaInicio <= fechaFin) {
                    if (fechaInicio.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
                        if (fechaFin.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
                            comprobado = true
                        }
                    }
                }
            }
        }
    }
    return comprobado
}

fun formatMedia(media: Double): String {
    val rounded = round(media * 100.0).toLong()
    val integerPart = rounded / 100
    val fraction = abs(rounded % 100)
    return "${integerPart}.${fraction.toString().padStart(2, '0')}"
}

fun formatDecimal1(value: Double): String {
    val rounded = round(value * 10.0).toLong()
    val integerPart = rounded / 10
    val fraction = abs(rounded % 10)
    return "${integerPart}.${fraction.toString().padStart(1, '0')}"
}

fun formatPercent(value: Double): String {
    val rounded = round(value).toLong()
    return "$rounded%"
}

fun calcularMediaAnio(anio: Anio): Double? {
    val asignaturas = anio.lista_asignaturas?.values ?: return null
    var sumaPonderada = 0.0
    var sumaPesos = 0.0

    asignaturas.forEach { asignatura ->
        val mediaAsignatura = asignatura.media ?: return@forEach
        val peso = asignatura.creditos?.takeIf { it > 0 } ?: 1
        sumaPonderada += mediaAsignatura * peso
        sumaPesos += peso
    }

    if (sumaPesos <= 0.0) return null
    return sumaPonderada / sumaPesos
}

fun calcularMediaConjunta(
    seleccionados: Map<String, Anio>,
    porcentajes: Map<String, String>,
): Double? {
    var acumulado = 0.0
    var pesoTotal = 0.0

    seleccionados.values.forEach { anio ->
        val id = anio.id ?: return@forEach
        val peso = porcentajes[id]?.toDoubleOrNull() ?: 0.0
        val mediaAnio = calcularMediaAnio(anio) ?: return@forEach

        if (peso > 0) {
            acumulado += mediaAnio * peso
            pesoTotal += peso
        }
    }

    return if (pesoTotal > 0) acumulado / pesoTotal else null
}

fun abbreviateName(name: String): String = name.split(" ")
    .filter { it.isNotEmpty() }
    .map { it.first().uppercase() }
    .joinToString("")
    .take(3)

fun abreviarNombre(nombre: String): String =
    nombre.trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.first().toString().uppercase() }
        .joinToString("")
        .ifEmpty { "A" }
        .take(2)

fun calcularPromedioNotas(notas: List<Notas>): Double {
    val totalPeso = notas.sumOf { it.porcentaje ?: 0.0 }
    if (totalPeso <= 0.0) return 0.0
    val ponderado = notas.sumOf { (it.nota ?: 0.0) * (it.porcentaje ?: 0.0) }
    return ponderado / totalPeso
}
